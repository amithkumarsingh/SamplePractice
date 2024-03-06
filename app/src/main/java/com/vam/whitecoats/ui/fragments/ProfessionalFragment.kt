package com.vam.whitecoats.ui.fragments

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.vam.whitecoats.R
import com.vam.whitecoats.ui.adapters.CustomViewPagerAdapter
import com.vam.whitecoats.ui.interfaces.OnDataLoadWithList
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ProfessionalFragment : Fragment() {

    private var tab_tablayout: TabLayout? = null
    private var tab_viewpager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var fragmentView = inflater!!.inflate(R.layout.professional_fragment_layout, container, false)

        // Create the object of Toolbar, ViewPager and
        // TabLayout and use “findViewById()” method*/
         tab_viewpager = fragmentView.findViewById<ViewPager>(R.id.viewpager)
         tab_tablayout = fragmentView.findViewById<TabLayout>(R.id.tabs)
        setupViewPager(tab_viewpager)


        return fragmentView;



    }

    private fun setupViewPager(tabViewpager: ViewPager?) {
        var adapter: CustomViewPagerAdapter = CustomViewPagerAdapter(requireActivity()!!.supportFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        var feedFragment=DashboardUpdatesFragment.newInstance(true)
        feedFragment.setDashboardResponse("")
        var opportunitiesFragment=DashboardUpdatesFragment.newInstance(true)
        opportunitiesFragment.setDashboardResponse("")
        adapter.addFragment(feedFragment, "Feeds")
        adapter.addFragment(ProfessionalSkillingFragment(), "Skilling")
        adapter.addFragment(opportunitiesFragment,"Opportunities")
        adapter.addFragment(ChannelsFragment.newInstance("","",""),"Partners")

        // setting adapter to view pager.
        if (tabViewpager != null) {
            tabViewpager.setAdapter(adapter)
        }


        tab_tablayout?.setupWithViewPager(tab_viewpager)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    companion object {
        @JvmStatic
        fun newInstance(s: String): ProfessionalFragment {
            val fragment = ProfessionalFragment()
            return fragment;

        }
    }




}