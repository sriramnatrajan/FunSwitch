package com.ultimate.funswitch.activity;

import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ultimate.funswitch.R;
import com.ultimate.funswitch.fragment.LaterFragment;
import com.ultimate.funswitch.fragment.TodayFragment;
import com.ultimate.funswitch.model.Model;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String studentName,studentGrade,studentNumber;

    Model as = new Model();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
        }


        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);




    }





    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putString("StudyTabActivity", studentNumber);
        TodayFragment fragobj = new TodayFragment();
        LaterFragment mClassworkFragment=new LaterFragment();
        mClassworkFragment.setArguments(bundle);
        fragobj.setArguments(bundle);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TodayFragment(), "Today");
        adapter.addFragment(new LaterFragment(), "Later");

        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TodayFragment mTodayFragment = new TodayFragment();
                    return mTodayFragment;

                default:

                    LaterFragment mLaterFragment= new LaterFragment();
                    return mLaterFragment;
            }
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
