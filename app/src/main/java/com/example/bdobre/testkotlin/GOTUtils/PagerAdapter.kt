package com.example.bdobre.testkotlin.GOTUtils

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by bdobre on 08/09/2017.
 */

class PageAdapter(internal var mNumTabs: Int, internal var mContext: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return  BooksFragment()
            1 -> return HousesFragment()
            2 -> {
                var bundle = Bundle()
                var prefs = PreferenceManager.getDefaultSharedPreferences(mContext)
                var list = prefs.getStringSet("fav", HashSet<String>())
                var characters = IntArray(list.size)
                var i = 0
                for( s in list){
                    characters[i++] = s.toInt()
                }
                bundle.putIntArray("characters", characters)
                var tab3 = CharactersFragment()
                tab3.arguments = bundle
                return tab3
            }
            else -> return Fragment()
        }
    }

    override fun getCount(): Int {
        return mNumTabs;
    }

}