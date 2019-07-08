package com.hy.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ResetPasswordFragment extends Fragment {

    public static ResetPasswordFragment newInstance(Bundle bundle) {
        ResetPasswordFragment frag = new ResetPasswordFragment();
        if (bundle != null) {
            frag.setArguments(bundle);
        }
        return frag;
    }
}
