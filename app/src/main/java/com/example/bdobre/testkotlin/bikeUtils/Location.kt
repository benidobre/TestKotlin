package com.example.bdobre.testkotlin.bikeUtils

/**
 * Created by bdobre on 05/09/2017.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {

    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

}