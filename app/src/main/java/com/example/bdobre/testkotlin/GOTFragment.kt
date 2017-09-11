package com.example.bdobre.testkotlin

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.design.widget.TabLayout;
import com.example.bdobre.testkotlin.GOTUtils.PageAdapter
import kotlinx.android.synthetic.main.fragment_got.view.*

/**
 * Created by bdobre on 05/09/2017.
 */
class GOTFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = inflater.inflate(R.layout.fragment_got, container, false)

        var tabLayout = rootView.tab_layout as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Books"))
        tabLayout.addTab(tabLayout.newTab().setText("Houses"))
        tabLayout.addTab(tabLayout.newTab().setText("Favorites"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = rootView.pager
        val adapter = PageAdapter(fm = activity.supportFragmentManager, mNumTabs = tabLayout.tabCount, mContext = context)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
        })



        return rootView
    }
}

