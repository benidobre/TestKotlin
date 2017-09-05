package com.example.bdobre.testkotlin.bikeUtils

/**
 * Created by bdobre on 05/09/2017.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BikeLocation {


    @SerializedName("href")
    @Expose
    var href: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("location")
    @Expose
    var location: Location? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("gbfs_href")
    @Expose
    var gbfsHref: String? = null
}

