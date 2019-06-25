package com.fourB.library.RequestBook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter{

    int nNumOfTabs;
    private SearchRequestBookFragment mSearchRequestBookFragment;
    private SelfRequestBookFragment mSelfRequestBookFragment;

    TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.nNumOfTabs = NoofTabs;

        mSearchRequestBookFragment = new SearchRequestBookFragment();
        mSelfRequestBookFragment = new SelfRequestBookFragment();
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mSearchRequestBookFragment;
            case 1:
                return mSelfRequestBookFragment;
            default:
                return null;
        }
    }
}