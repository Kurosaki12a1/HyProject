package com.hy.project.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hy.project.R;
import com.hy.project.fragment.LoginFragment;

public class AccountActivity extends AppCompatActivity {

    public static final String LOGIN_TAG = "LOGIN";
    public static final String REGISTER_TAG = "REGISTER";
    public static final String RESETPASSWORD_TAG = "RESET_PASSWORD";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bindView();
        renderUI( LoginFragment.newInstance(null), LOGIN_TAG);
    }

    private void bindView() {

    }

    public void changeToHomeActivity(){
        Intent intent = new Intent(AccountActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void renderUI(Fragment replaceFragment, String tag) {
        if (replaceFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, replaceFragment)
                    .addToBackStack(tag)
                    .commit();

        }
    }
}
