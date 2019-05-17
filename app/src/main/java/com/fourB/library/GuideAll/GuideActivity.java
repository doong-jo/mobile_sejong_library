package com.fourB.library.GuideAll;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.fourB.library.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        setTitle(getString(R.string.menu_usage));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ViewPager mGuidePager = (ViewPager) findViewById(R.id.viewPager_guide);
        mGuidePager.setOffscreenPageLimit(2);

        GuideViewPagerAdapter mGuideAdapter = new GuideViewPagerAdapter(getSupportFragmentManager());

        GuideFragment mIpadGuideFragment = new GuideFragment(R.layout.fragment_guide_ipad_borrow);
        GuideFragment mFloorGuideFragment = new GuideFragment(R.layout.fragment_guide_floor_use);
        GuideFragment mBorrowGuideFragment = new GuideFragment(R.layout.fragment_guide_book_borrow);

        mGuideAdapter.addPage(mFloorGuideFragment);
        mGuideAdapter.addPage(mBorrowGuideFragment);
        mGuideAdapter.addPage(mIpadGuideFragment);
        mGuidePager.setAdapter(mGuideAdapter);
    }

    class GuideViewPagerAdapter extends FragmentStatePagerAdapter{
        ArrayList<Fragment> pages = new ArrayList<>();

        public GuideViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addPage(Fragment page){
            pages.add(page);
        }

        @Override
        public Fragment getItem(int i) {
            return pages.get(i);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
