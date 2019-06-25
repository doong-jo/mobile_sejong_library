package com.fourB.library.RequestBook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter{

    int nNumOfTabs;

    TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.nNumOfTabs = NoofTabs;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchRequestBookFragment();
            case 1:
                return new SelfRequestBookFragment();
            default:
                return null;
        }
    }
}