package com.hy.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    public static SettingsFragment getInstance(Bundle bundle){
        SettingsFragment frag = new SettingsFragment();
        if(bundle!=null){
            frag.setArguments(bundle);
        }
        return frag;
    }
}
