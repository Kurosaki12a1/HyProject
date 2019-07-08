package com.hy.project.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hy.project.R;
import com.hy.project.fragment.ChatFragment;
import com.hy.project.fragment.LoginFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account); //Use same layout
        if(getIntent()!=null){
            String roomName = getIntent().getStringExtra("ROOM_NAME");
            String roomId  = getIntent().getStringExtra("ROOM_ID");
            Bundle bundle = new Bundle();
            bundle.putString("ROOM_NAME",roomName);
            bundle.putString("ROOM_ID",roomId);
            renderUI( ChatFragment.getInstance(bundle), "Chat");
        }


    }

    private void renderUI(Fragment replaceFragment, String tag) {
        if (replaceFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, replaceFragment)
                    .addToBackStack(tag)
                    .commit();

        }
    }
}
