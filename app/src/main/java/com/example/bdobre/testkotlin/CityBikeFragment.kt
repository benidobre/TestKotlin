package com.example.bdobre.testkotlin

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView;
import android.widget.Toast
import com.example.bdobre.testkotlin.bikeUtils.BikeAdapter
import com.example.bdobre.testkotlin.bikeUtils.BikeResponse
import com.example.bdobre.testkotlin.bikeUtils.BikeService
import kotlinx.android.synthetic.main.fragment_city_bikes.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bdobre on 05/09/2017.
 */
class CityBikeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var bikeAdapter: BikeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_city_bikes, container, false)

        recyclerView = rootView.recyclerview_bike
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        bikeAdapter = BikeAdapter(activity as BikeAdapter.BikeAdapterOnClickHandler)
        recyclerView.adapter = bikeAdapter

        val bikeCall = BikeService.Get()?.getData()
        bikeCall?.enqueue(object : Callback<BikeResponse> {
            override fun onResponse(call: Call<BikeResponse>?, response: Response<BikeResponse>?) {
                if(response != null && response.isSuccessful) {
                    bikeAdapter.setData(response.body().bikes)
                    bikeAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BikeResponse>?, t: Throwable?) {
                Toast.makeText(context,"eroare 2", Toast.LENGTH_SHORT).show()
            }

        })


        return rootView
    }
}

