package com.example.bdobre.testkotlin

import android.app.SearchManager
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.SearchView;
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.bdobre.testkotlin.bikeUtils.BikeResponse
import com.example.bdobre.testkotlin.weatherUtils.*
import kotlinx.android.synthetic.main.fragment_weather.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bdobre on 05/09/2017.
 */
class WeatherFragment : Fragment(), SearchView.OnQueryTextListener {

    lateinit var recyclerView : RecyclerView
    lateinit var weatherAdapter : WeatherAdapter
    var woeid = 0
    lateinit var listView : ListView
    lateinit var cityName : TextView
    lateinit var cityAdapter : CitySearchAdapter
    lateinit var searchMenuItem : MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_weather, container, false)

        setHasOptionsMenu(true)

        var bundle = arguments
        val longitude = bundle.getDouble("longitude");
        val latitude = bundle.getDouble("latitude");

        cityName = rootView.weather_city_name
        listView = rootView.listview_weather
        cityAdapter = CitySearchAdapter(context, emptyList())
        listView.adapter = cityAdapter
        listView.isTextFilterEnabled = true
        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                var current = cityAdapter.getItem(i)
                val wCall = WeatherService.Get()?.getWeather(current.woeid)
                wCall?.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                        if(response != null && response.isSuccessful) {
                            cityAdapter.clear()
                            val wr = response.body()
                            val list = wr.consolidatedWeather
                            cityName.text = wr.title
                            weatherAdapter.setData(list)
                            listView.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            cityName.visibility = View.VISIBLE
                            submitSearch()
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                        Toast.makeText(context,"eroare 2", Toast.LENGTH_SHORT).show()
                    }

                })

            }
        })

        recyclerView = rootView.recyclerview_weather
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        weatherAdapter = WeatherAdapter(activity as WeatherAdapter.WeatherAdapterOnClickHandler)
        recyclerView.adapter = weatherAdapter

        val nearCall = WeatherService.Get()?.getNearCities("$latitude,$longitude")
        nearCall?.enqueue(object : Callback<List<WeatherLocation>> {
            override fun onResponse(call: Call<List<WeatherLocation>>?, response: Response<List<WeatherLocation>>?) {
                if (response != null && response.isSuccessful){
                    val list = response?.body()
                    if (list != null) {
                        woeid = list[0].woeid
                    }

                    val weatherCall = WeatherService.Get()?.getWeather(woeid)
                    weatherCall?.enqueue(object : Callback<WeatherResponse> {
                        override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                            if(response != null && response.isSuccessful){
                                val wr = response.body()
                                val list = wr.consolidatedWeather
                                cityName.text = wr.title
                                weatherAdapter.setData(list)
                            }
                        }

                        override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {

                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<WeatherLocation>>?, t: Throwable?) {
            }
        })
        





        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_menu, menu)
        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchMenuItem = menu.findItem(R.id.search_item)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        submitSearch()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText ==null || newText.length == 0){
            listView.clearTextFilter()
        } else if(newText.length > 2) {
            listView.setFilterText(newText)
        } else if(newText.length == 2) {
            val cityCall = WeatherService.Get()?.getCities(newText)
            cityCall?.enqueue(object : Callback<List<CitySearch>> {

                override fun onResponse(call: Call<List<CitySearch>>?, response: Response<List<CitySearch>>?) {
                    if(response != null && response.isSuccessful) {
                        val list = response.body()
                        cityAdapter.clear()
                        if(list != null && !list.isEmpty()){
                            cityAdapter.addAll(list)
                            listView.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                            cityName.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<List<CitySearch>>?, t: Throwable?) {

                }
            })
        }

        return false
    }

    fun submitSearch() {
        if(searchMenuItem != null) {
            searchMenuItem.collapseActionView()
            val sv = searchMenuItem.actionView as SearchView
            sv.setQuery("", false)
            listView.clearTextFilter()
        }
    }
}

