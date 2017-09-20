package com.clock.sunyangyang.ss.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clock.sunyangyang.ss.R;

/**
 * Created by sunyangyang on 17/9/15.
 */

public class ThemeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        return view;
    }
}
