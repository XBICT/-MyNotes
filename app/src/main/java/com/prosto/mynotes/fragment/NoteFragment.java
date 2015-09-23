package com.prosto.mynotes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prosto.mynotes.R;

public class NoteFragment extends Fragment {
        private static final int LAYOUT = R.layout.note_fragment;
        private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;

    }

        public static NoteFragment getInstance(){
            Bundle args = new Bundle();
            NoteFragment fragment = new NoteFragment();
            fragment.setArguments(args);


            return fragment;
        }

    }


