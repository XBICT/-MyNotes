package com.prosto.mynotes.adapter;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.prosto.mynotes.Constants;
import com.prosto.mynotes.MainActivity;
import com.prosto.mynotes.fragment.TestFragment;
import com.prosto.mynotes.fragment.NoteFragment;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter{

    private String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs = new String[]{
                "Нотатки",
                "Нагадування",
                "Календар"
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 0:
                return NoteFragment.getInstance();

            case 1:
                return TestFragment.getInstance();

            case 2:
                return TestFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
