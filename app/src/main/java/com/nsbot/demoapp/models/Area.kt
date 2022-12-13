package com.nsbot.demoapp.models
import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("city_id") val cityId: Int,
    @SerializedName("pincode") val pincode: Int,
)