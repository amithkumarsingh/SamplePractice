package com.vam.whitecoats.ui.adapters;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vam.whitecoats.ui.fragments.ExploreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venuv on 4/24/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final FragmentManager fragmentMangaer;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        fragmentMangaer=manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    public Fragment getCurrentFragment(int position) {
        return mFragmentList.get(position);
    }

    public void replaceFragment(int position,Fragment fragment1,String title,Fragment fragment2){
        if(fragmentMangaer!=null){
            mFragmentList.remove(fragment1);
            fragmentMangaer.beginTransaction().replace(android.R.id.content,fragment2,title).commit();
            //addFrag(fragment2,title);
        }
        notifyDataSetChanged();
    }
}
