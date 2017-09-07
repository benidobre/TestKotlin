package com.example.bdobre.testkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.example.bdobre.testkotlin.bikeUtils.BikeAdapter
import com.example.bdobre.testkotlin.bikeUtils.BikeDetailsFragment
import com.example.bdobre.testkotlin.bikeUtils.BikeLocation
import com.example.bdobre.testkotlin.drawerUtils.DataModel
import com.example.bdobre.testkotlin.drawerUtils.DrawerItemCustomAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BikeAdapter.BikeAdapterOnClickHandler {

    lateinit var mDrawerList: ListView
    lateinit var mNavigationDrawerItemTitles: Array<String>
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mTitle: CharSequence
    lateinit var mDrawerTitle: CharSequence
    lateinit var mToolbar: android.support.v7.widget.Toolbar
    lateinit var mDrawerToggle: android.support.v7.app.ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerTitle = title
        mTitle = mDrawerTitle
        mNavigationDrawerItemTitles = resources.getStringArray(R.array.navigation_drawer_items_array)
        mDrawerLayout = drawer_layout as DrawerLayout
        mDrawerList = left_drawer as ListView

        setupToolbar()

        val drawerItem = arrayOf(
                DataModel((R.drawable.bike), "City bikes"),
                DataModel((R.drawable.weather), "Weather"),
                DataModel((R.drawable.got), "Game of Thrones"))

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        val adapter = DrawerItemCustomAdapter(this, R.layout.my_list_view_item_row, drawerItem)
        mDrawerList.adapter = adapter
        mDrawerList.setOnItemClickListener(DrawerItemClickListener())

        mDrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        setupDrawerToggle()
        mDrawerLayout.setDrawerListener(mDrawerToggle)


        supportFragmentManager.beginTransaction().replace(R.id.content_frame, CityBikeFragment() as Fragment).commit()


    }

    inner class DrawerItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectItem(position)
        }
    }

    fun selectItem(position: Int){
        var fragment:Fragment? = null
        when(position){
            0 -> fragment = CityBikeFragment() as Fragment
            1 -> fragment = WeatherFragment() as Fragment
            2 -> fragment = GOTFragment() as Fragment
        }
        if (fragment != null){
            supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit()
            mDrawerList.setItemChecked(position, true)
            mDrawerList.setSelection(position)
            setTitle(mNavigationDrawerItemTitles?.get(position))
            mDrawerLayout.closeDrawer(mDrawerList)
        } else {
            Log.e("MainActivity", "Error in creating fragment")
        }
    }

    internal fun setupToolbar() {
        mToolbar = toolbar as Toolbar
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)

    }

    internal fun setupDrawerToggle() {
        mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name)
        mDrawerToggle.syncState()
    }

    override fun onClick(bikeData: BikeLocation?) {
        val fragment = BikeDetailsFragment()
        val bundle = Bundle()
        bundle.putString("name", bikeData?.name)
        val location = bikeData?.location
        if(location !=  null) {
            bundle.putString("city", location.city)
            bundle.putString("country", location.country)
            bundle.putDouble("longitude", location.longitude)
            bundle.putDouble("latitude", location.latitude)
        }
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit()

        Toast.makeText(this, bikeData?.name, Toast.LENGTH_SHORT).show()
    }
}
