package com.nsbot.demoapp.models

import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class StoreTime {

    @SerializedName("store_open") var open: Boolean = true

    @SerializedName("mon") var monday: Boolean = true
    @SerializedName("mon_open") var mondayOpen: String = ""
    @SerializedName("mon_close") var mondayClose: String = ""

    @SerializedName("tue") var tuesday: Boolean = true
    @SerializedName("tue_open") var tuesdayOpen: String = ""
    @SerializedName("tue_close") var tuesdayClose: String = ""

    @SerializedName("wed") var wednesday: Boolean = true
    @SerializedName("wed_open") var wednesdayOpen: String = ""
    @SerializedName("wed_close") var wednesdayClose: String = ""

    @SerializedName("thr") var thursday: Boolean = true
    @SerializedName("thr_open") var thursdayOpen: String = ""
    @SerializedName("thr_close") var thursdayClose: String = ""

    @SerializedName("fri") var friday: Boolean = true
    @SerializedName("fri_open") var fridayOpen: String = ""
    @SerializedName("fri_close") var fridayClose: String = ""

    @SerializedName("sat") var saturday: Boolean = true
    @SerializedName("sat_open") var saturdayOpen: String = ""
    @SerializedName("sat_close") var saturdayClose: String = ""

    @SerializedName("sun") var sunday: Boolean = true
    @SerializedName("sun_open") var sundayOpen: String = ""
    @SerializedName("sun_close") var sundayClose: String = ""

    companion object {

        private val inputTimeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        private val displayFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

        //to format time into 12 hours
        fun getDisplayTime(time: String): String {
            return try {
                val date = inputTimeFormat.parse(time)
                displayFormat.format(date!!)
            }catch (e: Exception) {
                e.printStackTrace()
                time
            }
        }

    }
}
