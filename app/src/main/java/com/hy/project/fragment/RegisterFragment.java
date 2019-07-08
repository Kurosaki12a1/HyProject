package com.hy.project.fragment;

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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hy.project.R;
import com.hy.project.activity.AccountActivity;
import com.hy.project.model.Profile;

import static com.hy.project.fragment.LoginFragment.MESSAGE_INVALID_PASSWORD;
import static com.hy.project.fragment.LoginFragment.MESSAGE_LOGIN_SUCCESSFUL;
import static com.hy.project.fragment.LoginFragment.MESSAGE_NO_INPUT_EMAIL;
import static com.hy.project.fragment.LoginFragment.TAG;

public class RegisterFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnSignUp, btnResetPassword, btnLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private static final String MESSAGE_REGISTER_SUCCESSFUL = "MESSAGE_REGISTER_SUCCESSFUL";
    private static final String MESSAGE_REGISTER_FAILURE = "MESSAGE_REGISTER_FAILURE";
    private static final String DEFAULT_AVATAR = "https://firebasestorage.googleapis.com/v0/b/silentmessage-3990f.appspot.com/o/default_avatar.jpg?alt=media&token=b373d033-17c0-41be-a548-839509d30e46";
    public static final String ACCOUNT_PATH ="ALL_PROFILE_DATABASE";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    static RegisterFragment newInstance(Bundle bundle) {
        RegisterFragment frag = new RegisterFragment();
        if (bundle != null) {
            frag.setArguments(bundle);
        }
        return frag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        mAuth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });
    }

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
            case MESSAGE_REGISTER_SUCCESSFUL:
                Toast.makeText(getActivity(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_INVALID_PASSWORD:
                Toast.makeText(getActivity(), "Mật khẩu phải 6 ký tự trở lên.", Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_NO_INPUT_EMAIL:
                Toast.makeText(getActivity(), "Xin vui lòng nhập email hợp lệ.", Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_REGISTER_FAILURE:
                Toast.makeText(getActivity(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void signUp(final String email, String password) {
        if (checkValidate(email, password)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                showMessage(MESSAGE_REGISTER_SUCCESSFUL);
                                String userId = mAuth.getCurrentUser().getUid();
                                String[] part = email.split("@");
                                Profile profile = new Profile(
                                        userId,
                                        part[0],
                                        DEFAULT_AVATAR,
                                        email
                                        );
                                sendEmailVerify();
                                DatabaseReference dbProfile = FirebaseDatabase.getInstance().getReference(ACCOUNT_PATH);
                                dbProfile.child(userId).setValue(profile);
                                ((AccountActivity)getActivity()).changeToHomeActivity();

                            } else {
                                showMessage(MESSAGE_REGISTER_FAILURE);
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private void sendEmailVerify() {
        if(mAuth.getCurrentUser()!=null){
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG,"Send Email Verify Done");
                    }
                    else{
                        Log.d(TAG,"Send Email Fail "+task.getException());
                    }
                }
            });
        }

    }




    private View bindViewById(int id) {
        return getView().findViewById(id);
    }

    private void bindView() {
        etEmail = (EditText) bindViewById(R.id.email);
        etPassword = (EditText) bindViewById(R.id.password);
        btnLogin = (Button) bindViewById(R.id.sign_in_button);
        btnResetPassword = (Button) bindViewById(R.id.btn_reset_password);
        btnSignUp = (Button) bindViewById(R.id.sign_up_button);
        progressBar = (ProgressBar) bindViewById(R.id.progressBar);
    }
}
