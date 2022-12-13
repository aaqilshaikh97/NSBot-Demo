package com.nsbot.demoapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class  Seller : Serializable {

    @SerializedName("id") var id :String  = ""

    @SerializedName("business_name") var businessName :String  = ""

    @SerializedName("gst_no") var gstNo :String?  = null

    @SerializedName("mobile") var mobile: String? = null

    @SerializedName("logo") var logo :String?  = null

    @SerializedName("loc_lat")
    var latitude: Double? = null

    @SerializedName("loc_lon")
    var longitude: Double? = null

    @SerializedName("verified") var verified: Boolean = false

    @SerializedName("active") var active: Boolean = false

    @SerializedName("address") var address: String = ""

    @SerializedName("landmark") var landMark: String = ""

    @SerializedName("pincode")
    var pinCode: Int = 0

    @SerializedName("city") var city: City? = null

    @SerializedName("state") var state: State? = null

    @SerializedName("country") var country: Country? = null

    @SerializedName("area") var area: Area? = null

    @SerializedName("photo_1") var photo1: String? = null

    @SerializedName("photo_2") var photo2: String? = null

    @SerializedName("photo_3") var photo3: String? = null

    @SerializedName("pan") var pan: String? = null
    @SerializedName("license") var license: String? = null
    @SerializedName("aadhar") var aadhar: String? = null

    @SerializedName("upi_app") var upiApp: String? = null
    @SerializedName("upi_phone") var upiPhone: String? = null
    @SerializedName("upi_name") var upiName: String? = null

    @SerializedName("timing") var timing: StoreTime? = null

    @SerializedName("cityId")
    var cityId: Int = 0
    @SerializedName("countryId")
    var countryId: Int = 0
    @SerializedName("stateId")
    var stateId: Int = 0
    @SerializedName("areaId")
    var areaId: Int = 0

    var account: Account? = null
}