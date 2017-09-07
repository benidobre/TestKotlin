package com.example.bdobre.testkotlin.weatherUtils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.weather_list_item.view.*

/**
 * Created by bdobre on 07/09/2017.
 */

class WeatherAdapter(internal val onClickHandler: WeatherAdapterOnClickHandler) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

    var list : List<ConsolidatedWeather>? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) : WeatherViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.weather_list_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun setData(data: List<ConsolidatedWeather>) {
        list = data
        notifyDataSetChanged()
    }


    interface WeatherAdapterOnClickHandler{
        fun onClick(weatherData: ConsolidatedWeather?)
    }

    inner class WeatherViewHolder(internal val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val date = view.weather_date
        val state = view.weather_state
        val min = view.weather_min
        val max = view.weather_max

        fun bind(position: Int){
            val weatherData = list?.get(position)
            view.setOnClickListener(this)
            if(weatherData != null) {
                state.text = weatherData.getWeatherStateName();
                date.text = weatherData.getApplicableDate()
                min.text = Math.floor(weatherData.getMinTemp()).toString()
                max.text = Math.floor(weatherData.getMaxTemp()).toString()
            }

        }
        override fun onClick(p0: View?) {
            onClickHandler.onClick(list?.get(adapterPosition));
        }
    }

}