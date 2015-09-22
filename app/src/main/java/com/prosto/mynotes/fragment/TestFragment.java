package com.prosto.mynotes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prosto.mynotes.MainActivity;
import com.prosto.mynotes.NewNoteActivity;
import com.prosto.mynotes.R;

import java.util.Map;

public class TestFragment extends Fragment{
    private static final int LAYOUT = R.layout.test_fragment;
    private View view;

    public static TestFragment getInstance(){
        Bundle args = new Bundle();
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}
