package com.example.bdobre.testkotlin.weatherUtils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.listview_weather_item.view.*

/**
 * Created by bdobre on 07/09/2017.
 */

class CitySearchAdapter(c: Context, list : List<CitySearch>) : ArrayAdapter<CitySearch>(c,0,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var listItemView = convertView
        if(listItemView == null){
            listItemView = LayoutInflater.from(context).inflate( R.layout.listview_weather_item,parent,false)
        }

        listItemView?.listview_item_name?.text = getItem(position).title

        return listItemView as View

    }
}