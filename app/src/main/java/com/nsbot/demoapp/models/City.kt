package com.nsbot.demoapp.models
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("state_id") val stateId: Int
)