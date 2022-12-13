package com.nsbot.demoapp.models
import com.google.gson.annotations.SerializedName

data class State(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country_id") val countryId: Int
)