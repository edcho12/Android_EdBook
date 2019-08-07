package com.be.edbook.view.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.be.edbook.R;
import com.be.edbook.view.ui.MainDetailFragment;
import com.be.edbook.view.ui.MainHomeFragment;


public class MainSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_main_home, R.string.tab_main_detail};
    private final Context mContext;

    public MainSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                MainHomeFragment homeFragment = new MainHomeFragment();
                return homeFragment;

            case 1:
                MainDetailFragment detailFragment = new MainDetailFragment();
                return detailFragment;
        }


        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}