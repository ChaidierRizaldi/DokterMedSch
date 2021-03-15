package com.example.dokterandroidmedsch.ui.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    int totalTabs;

    public LoginAdapter(FragmentManager fm, int totalTabs){
        super (fm);
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {

        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;

            default:
                return null;
        }
    }
}
