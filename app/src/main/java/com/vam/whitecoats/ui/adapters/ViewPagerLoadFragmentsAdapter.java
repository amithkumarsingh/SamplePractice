package com.vam.whitecoats.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewPagerLoadFragmentsAdapter extends FragmentStateAdapter {
    /*Getting list of fragments in the View pager adapter and loading the same baed on the position*/
    private List<Fragment> fragmentList;
    private List<String> mFragmentTitleList;
    private String callConstructorFrom;

    public ViewPagerLoadFragmentsAdapter(FragmentActivity activity,
                                         List<Fragment> fragmentList, List<String> mFragmentTitleList, String callConstructorFrom) {
        super(activity);
        this.fragmentList = fragmentList;
        this.mFragmentTitleList = mFragmentTitleList;
        this.callConstructorFrom = callConstructorFrom;
    }

    public ViewPagerLoadFragmentsAdapter(@NonNull Fragment fragmentActivity,
                                         List<Fragment> fragmentList, List<String> mFragmentTitleList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
        this.mFragmentTitleList = mFragmentTitleList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        /*Getting list of fragments in the View pager adapter and loading the same baed on the position*/
        if (fragmentList != null && fragmentList.size() > 0) {
            try {
                return fragmentList.get(position);
            } catch (Exception e) {
                return fragmentList.get(fragmentList.size() - 1);
            }
        }
        return new Fragment();
       /* switch (position) {
            case 0:
                return ProfessionalFeedsAndOpportuniesFragments.newInstance("fromProfessionalFeeds");
            case 1:
                return ContentFeedsFragment.newInstance("fromProfessionalSkilling");
            case 2:
                return professionalJobsFragment;
            default:
                return ContentChannelsFragment.newInstance("", "", "PROFESSIONAL_TAB");
        }*/

    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public CharSequence getPageTitle(int position) {
        if (mFragmentTitleList != null) {
            return mFragmentTitleList.get(position);
        }
        return "";
    }

    public Fragment getCurrentFragment(int position) {
        return fragmentList.get(position);
    }

    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public int getCount() {
        return fragmentList.size();
    }

}
