package com.example.bdobre.testkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
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
import com.example.bdobre.testkotlin.weatherUtils.ConsolidatedWeather
import com.example.bdobre.testkotlin.weatherUtils.WeatherAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest;
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import com.example.bdobre.testkotlin.GOTUtils.*
import com.google.android.gms.tasks.OnSuccessListener
import java.util.ArrayList

class MainActivity : AppCompatActivity(), BikeAdapter.BikeAdapterOnClickHandler, WeatherAdapter.WeatherAdapterOnClickHandler,
                BooksAdapter.BooksAdapterOnClickHandler, CharactersAdapter.CharacterOnClickHandler, HousesAdapter.HouseAdapterOnClickHandler{


    lateinit var mDrawerList: ListView
    lateinit var mNavigationDrawerItemTitles: Array<String>
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mTitle: CharSequence
    lateinit var mDrawerTitle: CharSequence
    lateinit var mToolbar: android.support.v7.widget.Toolbar
    lateinit var mDrawerToggle: android.support.v7.app.ActionBarDrawerToggle
    var currentLat = 0.0
    var currentLong = 0.0
    lateinit var mFusedLocationClient : FusedLocationProviderClient

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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        supportFragmentManager.beginTransaction().replace(R.id.content_frame, CityBikeFragment() as Fragment).commit()
        getLocation()

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
            1 -> {
                fragment = WeatherFragment() as Fragment
                val bundle = Bundle()
                bundle.putDouble("longitude",currentLong);
                bundle.putDouble("latitude",currentLat);
                fragment.arguments = bundle
                }
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

    override fun onClick(weatherData: ConsolidatedWeather?) {
        Toast.makeText(this, weatherData?.weatherStateName, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(book: Book?) {
        var bundle = Bundle()
        var listCharacters = book?.characters
        if(listCharacters != null){
            var characters = IntArray(listCharacters?.size ?: 0)
            var i = 0
            for(ch in listCharacters) {
                var id = ch.subSequence("https://www.anapioficeandfire.com/api/characters/".length, ch.length)
                characters[i++] = id.toString().toInt()
            }
            bundle.putIntArray("characters",characters)
        }
        var fragment = CharactersFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit()

    }

    override fun onClick(house: House?) {
        var bundle = Bundle()
        if(house != null) {
            var listCharacters = house.swornMembers
            if(!house.currentLord.isEmpty()) {
                listCharacters.add(0, house.currentLord)
            }
            if(!house.heir.isEmpty()) {
                listCharacters.add(0, house.heir)
            }
            if(!house.founder.isEmpty()) {
                listCharacters.add(0, house.founder)
            }


            var characters = IntArray(listCharacters.size)
            var i = 0
            for(ch in listCharacters) {
                var id = ch.subSequence("https://www.anapioficeandfire.com/api/characters/".length, ch.length)
                characters[i++] = id.toString().toInt()
            }
            bundle.putIntArray("characters",characters)

        }
        var fragment = CharactersFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit()
    }

    override fun onClick(character: Character?) {
        var bundle = Bundle()
        bundle.putString("name", character?.name)
        bundle.putString("born",character?.born)
        var id = character?.url?.subSequence("https://www.anapioficeandfire.com/api/characters/".length, character.url.length)
        bundle.putInt("id", id.toString().toInt())
        bundle.putStringArrayList("aliases",character?.aliases as ArrayList<String>)
        var fragment = CharacterDetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit()
    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET)
                        ,10)
            }
            return
        }
        mFusedLocationClient.lastLocation.addOnSuccessListener(this, object : OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
                if (location != null) {
                    currentLat = location.latitude
                    currentLong = location.longitude
                }
            }
        })
    }
}
