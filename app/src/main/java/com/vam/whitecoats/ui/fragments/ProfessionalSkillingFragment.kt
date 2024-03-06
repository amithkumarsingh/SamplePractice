package com.vam.whitecoats.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vam.whitecoats.R

class ProfessionalSkillingFragment : Fragment() {


    fun newInstance(customArgs: Bundle): ProfessionalSkillingFragment? {
        val fragment = ProfessionalSkillingFragment()
        val args = Bundle()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = inflater!!.inflate(R.layout.professional_skilling_fragment, container, false)

        return fragmentView;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var fragment = DashboardUpdatesFragment.newInstance( true)
        fragment.setDashboardResponse("")

        val fragmentManager: FragmentManager = requireActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.child_fragment_container, fragment)
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(s: String): ProfessionalSkillingFragment {
            val fragment = ProfessionalSkillingFragment()
            return fragment;

        }
    }


}