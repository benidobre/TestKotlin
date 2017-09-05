package com.example.bdobre.testkotlin.bikeUtils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.bike_list_item.view.*

/**
 * Created by bdobre on 05/09/2017.
 */

class BikeAdapter(internal val clickHandler: BikeAdapterOnClickHandler) : RecyclerView.Adapter<BikeAdapter.BikeViewHolder>() {

    var bikes: List<BikeLocation>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : BikeViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.bike_list_item, parent, false)
        return BikeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BikeViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(data: List<BikeLocation>?) {
        bikes = data
    }

    interface BikeAdapterOnClickHandler{
        fun onClick(bikeData: BikeLocation?)
    }

    override fun getItemCount(): Int {
        return bikes?.size ?: 0
    }

    inner class BikeViewHolder(internal val view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        var bikeTextView = view.tv_bike

        fun bind(position: Int) {
            var bikedata = bikes?.get(position)
            view.setOnClickListener(this)
            bikeTextView.text = bikedata?.location?.city
        }

        override fun onClick(view: View) {
            clickHandler.onClick(bikes?.get(adapterPosition))
        }
    }
}
