package com.example.bdobre.testkotlin.GOTUtils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.bike_list_item.view.*
import java.util.ArrayList
import java.util.List

/**
 * Created by bdobre on 08/09/2017.
 */

class HousesAdapter(internal var clickHandler: HouseAdapterOnClickHandler) : RecyclerView.Adapter<HousesAdapter.HousesViewHolder>() {

    var houses: List<House>? = ArrayList<House>() as List<House>

    override fun onBindViewHolder(holder: HousesViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousesViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.bike_list_item, parent, false)
        return HousesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return houses?.size ?: 0
    }

    fun setData(data: List<House>?) {
        houses = data
        notifyDataSetChanged()
    }

    fun getData() : List<House>? {
        return houses
    }

    interface HouseAdapterOnClickHandler {
        fun onClick(house: House?)
    }

    inner class HousesViewHolder(internal var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var houseTextView = view.tv_bike

        fun bind(position: Int) {
            view.setOnClickListener(this)
            houseTextView.text = houses?.get(position)?.name
        }
        override fun onClick(p0: View?) {
            clickHandler.onClick(houses?.get(adapterPosition))
        }

    }
}