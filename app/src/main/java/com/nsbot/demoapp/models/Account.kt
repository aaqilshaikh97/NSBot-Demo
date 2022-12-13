package com.nsbot.demoapp.models

import com.google.gson.annotations.SerializedName

class Account {

    @SerializedName("accountId")
    var accountId: String = ""

    @SerializedName("role")
    var role: String = ""

    @SerializedName("first_name")
    var firstName: String = ""

    @SerializedName("last_name")
    var lastName: String = ""

    @SerializedName("email")
    var email: String = ""

    @SerializedName("mobile")
    var mobile: String = ""

    @SerializedName("is_active")
    var isActive: Boolean = false

    @SerializedName("password")
    var password: String = ""

}