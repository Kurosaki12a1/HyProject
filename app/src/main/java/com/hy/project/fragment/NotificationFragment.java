package com.hy.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class NotificationFragment extends Fragment {

    public static NotificationFragment getInstance(Bundle bundle) {
        NotificationFragment frag = new NotificationFragment();
        if (bundle != null) {
            frag.setArguments(bundle);
        }
        return frag;
    }
}
