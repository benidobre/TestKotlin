package com.example.bdobre.testkotlin.GOTUtils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.got_houses.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by bdobre on 08/09/2017.
 */

class HousesFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var housesAdapter: HousesAdapter
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    var hasItems = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.got_houses, container, false)
        
        recyclerView = rootView.recyclerview_got_houses
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        housesAdapter = HousesAdapter(activity as HousesAdapter.HouseAdapterOnClickHandler)
        recyclerView.adapter = housesAdapter
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view:RecyclerView) {
                fetch(page+1);
            }
        }
        fetch(1)

        
        return rootView
    }
    
    fun fetch(page: Int) {
        var housesCall = GOTService.Get()?.getHouses(page, 50)
        housesCall?.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>?, response: Response<List<House>>?) {
                if(response != null && response.isSuccessful) {
                    var houses = response.body()
                    var list = housesAdapter.getData()
                    for(i in houses) {
                        list?.add(i)
                    }
                    housesAdapter.setData(list)
                    if(!hasItems) {
                        hasItems = true
                        recyclerView.addOnScrollListener(scrollListener)
                    }
                }
            }

            override fun onFailure(call: Call<List<House>>?, t: Throwable?) {

            }

        })
    }
}