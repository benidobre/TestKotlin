package com.example.bdobre.testkotlin.bikeUtils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.fragment_bike_detail.view.*

/**
 * Created by bdobre on 05/09/2017.
 */

class BikeDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_bike_detail, container, false)

        val bundle = arguments
        val longitude = bundle.getDouble("longitude")
        val latitude = bundle.getDouble("latitude")
        val name = bundle.getString("name")
        rootView.station_name.text = name
        rootView.station_location.text = bundle.getString("city") + ", " + bundle.getString("country")

        rootView.location_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val gmnIntentUri = Uri.parse("geo:$latitude,$longitude?q$name")

                val mapIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                if(mapIntent.resolveActivity(context.packageManager) != null){
                    startActivity(mapIntent)
                }
            }
        })

        return rootView
    }
}