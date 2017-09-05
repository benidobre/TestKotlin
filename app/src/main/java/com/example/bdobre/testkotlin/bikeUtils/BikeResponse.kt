package com.example.bdobre.testkotlin.bikeUtils

/**
 * Created by bdobre on 05/09/2017.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BikeResponse {

    @SerializedName("networks")
    @Expose
    var bikes: List<BikeLocation>? = null
}

