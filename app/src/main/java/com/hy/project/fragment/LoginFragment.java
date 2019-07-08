package com.hy.project.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hy.project.R;
import com.hy.project.activity.AccountActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.hy.project.activity.AccountActivity.REGISTER_TAG;
import static com.hy.project.activity.AccountActivity.RESETPASSWORD_TAG;

public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment";
    public static final String MESSAGE_LOGIN_SUCCESSFUL = "MESSAGE_LOGIN_SUCCESSFUL";
    public static final String MESSAGE_LOGIN_FAILURE = "MESSAGE_LOGIN_FAILURE";
    public static final String MESSAGE_NO_INPUT_EMAIL = "MESSAGE_NO_INPUT_EMAIL";
    public static final String MESSAGE_INVALID_PASSWORD = "MESSAGE_INVALID_PASSWORD";


    EditText etEmail, etPassword;
    CheckBox cbRememberPassword;
    Button btnLogin, btnResetPassword, btnSignUp;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    public LoginFragment() {

    }

    public static LoginFragment newInstance(Bundle bundle) {
        LoginFragment frag = new LoginFragment();
        if (bundle != null) {
            frag.setArguments(bundle);
        }
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        checkAlreadyLogin();
        bindView();
        initSaveAccount();
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loginAccount(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    ((AccountActivity) getActivity()).renderUI(RegisterFragment.newInstance(null), REGISTER_TAG);
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    ((AccountActivity) getActivity()).renderUI(ResetPasswordFragment.newInstance(null), RESETPASSWORD_TAG);
                }
            }
        });
    }

    private void loginAccount(final String email, final String password) {
        if (isEmailValid(email)) {
            if (checkValidate(email, password)) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    showMessage(MESSAGE_LOGIN_SUCCESSFUL);
                                    Log.d(TAG, "Login Successfully with account : " + mAuth.getCurrentUser());
                                    checkRememberPassword(email, password);
                                    goToHomeActivity();
                                } else {
                                    showMessage(MESSAGE_LOGIN_FAILURE);
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        } else {
            showMessage(MESSAGE_NO_INPUT_EMAIL);
        }
    }

    /**
     * Kiểm tra tài khoản có hợp lệ không
     *
     * @param email
     * @param password
     * @return boolean
     */

    private Boolean checkValidate(String email, String password) {
        if (email.isEmpty()) {
            showMessage(MESSAGE_NO_INPUT_EMAIL);
            return false;
        } else if (password.length() < 6) {
            showMessage(MESSAGE_INVALID_PASSWORD);
            return false;
        }
        return true;
    }

    private void showMessage(String tagShow) {
        switch (tagShow) {
            case MESSAGE_LOGIN_SUCCESSFUL:
                Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_LOGIN_FAILURE:
                Toast.makeText(getContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_NO_INPUT_EMAIL:
                Toast.makeText(getContext(), "Xin vui lòng nhập email hợp lệ.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //This for check "nhớ mật khẩu" or not
    private void checkRememberPassword(String email, String password) {
        if (cbRememberPassword.isChecked()) {
            if (getActivity() != null) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATA_SAVED_ACCOUNT", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("email", email);
                edit.putString("password", password);
                edit.apply();
            }
        } else {
            if (getActivity() != null) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATA_SAVED_ACCOUNT", MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("email", "");
                edit.putString("password", "");
                edit.apply();
            }
        }
    }

    /**
     * Kiểm tra email hợp lệ không
     *
     * @param email
     * @return
     */
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Kiểm tra đã đăng nhập chưa
     */

    private void checkAlreadyLogin() {
        if (mAuth.getCurrentUser() != null) {
            Log.d(TAG, "Already Login Account : " + mAuth.getCurrentUser());
            goToHomeActivity();
        }
    }

    /**
     * Code cho gọn
     *
     * @param id
     * @return
     */

    private View bindViewById(int id) {
        return getView().findViewById(id);
    }

    /**
     * Trường hợp nhớ mật khẩu
     */
    private void initSaveAccount() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATA_SAVED_ACCOUNT", MODE_PRIVATE);
            String txtEmail = sharedPreferences.getString("email", "");
            String txtPassword = sharedPreferences.getString("password", "");
            etEmail.setText(txtEmail);
            etPassword.setText(txtPassword);
            if (!txtEmail.equals("")) {
                cbRememberPassword.setChecked(false);
            }
        }
    }

    private void bindView() {
        etEmail = (EditText) bindViewById(R.id.email);
        etPassword = (EditText) bindViewById(R.id.password);
        cbRememberPassword = (CheckBox) bindViewById(R.id.checkBoxRemember);
        btnLogin = (Button) bindViewById(R.id.btn_login);
        btnResetPassword = (Button) bindViewById(R.id.btn_reset_password);
        btnSignUp = (Button) bindViewById(R.id.btn_signup);
        progressBar = (ProgressBar) bindViewById(R.id.progressBar);
    }

    private void goToHomeActivity() {
        if (getActivity() != null) {
            ((AccountActivity) getActivity()).changeToHomeActivity();
        }
    }
}
