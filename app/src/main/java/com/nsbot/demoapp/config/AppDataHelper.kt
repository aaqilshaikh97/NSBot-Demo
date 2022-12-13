package com.nsbot.demoapp.config

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nsbot.demoapp.models.*
import com.nsbot.demoapp.models.StoreTime

class AppDataHelper (context: Context) {

    private val preferences = context.getSharedPreferences("app-data", Context.MODE_PRIVATE)

    fun saveSeller(seller: Seller) {

        Log.e("save","seller ${Gson().toJson(seller)}")

        preferences.edit().apply {

            putString("sellerId", seller.id)
            putString("businessName", seller.businessName)
            putString("gstNo", seller.gstNo)
            putString("logo", seller.logo)
            putString("photo1", seller.photo1)
            putString("photo2", seller.photo2)
            putString("photo3", seller.photo3)
            putString("landMark", seller.landMark)
            putString("address", seller.address)


            putString("pan", seller.pan)
            putString("license", seller.license)
            putString("aadhar", seller.aadhar)

            putInt("area_id", seller.area?.id ?: 0)
            putString("area_name", seller.area?.name)
            putInt("area_city", seller.area?.cityId ?: 0)
            putInt("area_pincode", seller.area?.pincode ?: 0)

            putInt("state_id", seller.state?.id ?: 0)
            putString("state_name", seller.state?.name)
            putInt("state_country", seller.state?.countryId ?: 0)

            putInt("city_id", seller.city?.id ?: 0)
            putString("city_name", seller.city?.name)
            putInt("city_state", seller.city?.stateId ?: 0)

            putInt("country_id",  seller.country?.id ?: 0)
            putString("country_name",  seller.country?.name)
            putString("upi_app",  seller.upiApp)
            putString("upi_phone",  seller.upiPhone)
            putString("upi_name",  seller.upiName)


            putString("loc_lat",  seller.latitude.toString())
            putString("loc_lon",  seller.longitude.toString())

            putBoolean("active", seller.active)
            putBoolean("verified", seller.verified)
            apply()

        }

        if (seller.timing != null) {
            saveTiming(seller.timing!!)
        }
    }


    fun getSeller(): Seller {

        val seller = Seller()

        seller.id = preferences.getString("id", "")!!
        seller.businessName = preferences.getString("businessName", "")!!
        seller.gstNo = preferences.getString("gstNo", "")!!
        seller.logo = preferences.getString("logo","")!!
        seller.photo1 = preferences.getString("photo1", "")!!
        seller.photo2 = preferences.getString("photo2", "")!!
        seller.photo3 = preferences.getString("photo3", "")!!
        seller.landMark = preferences.getString("landMark", "")!!
        seller.address = preferences.getString("address","")!!
        seller.area = getArea()
        seller.state = getState()
        seller.city = getCity()
        seller.country = getCountry()
        seller.active = preferences.getBoolean("active", true)
        seller.verified = preferences.getBoolean("verified", false)

        seller.latitude = preferences.getString("loc_lat", "")?.toDoubleOrNull()
        seller.longitude = preferences.getString("loc_lon","")?.toDoubleOrNull()

        seller.account = getAccount()

        return seller
    }

    fun saveAccount(account: Account) {

        preferences.edit().apply {
            putString("accountId", account.accountId)
            putString("firstName", account.firstName)
            putString("lastName", account.lastName)
            putString("email", account.email)
            putString("mobile", account.mobile)
            putString("role", account.role)
            putBoolean("isActive", account.isActive)
            putString("password", account.password)
            apply()
        }

    }

    fun getAccount(): Account {
        val account = Account()
        account.accountId = preferences.getString("accountId","")!!
        account.firstName = preferences.getString("fistName","")!!
        account.lastName = preferences.getString("lastName", "")!!
        account.email = preferences.getString("email", "")!!
        account.mobile = preferences.getString("mobile", "")!!
        account.role = preferences.getString("role", "")!!
        account.isActive = preferences.getBoolean("isActive", false)
        account.password = preferences.getString("password","")!!
        return account
    }

    fun saveTiming(timing: StoreTime) {

        preferences.edit().apply {
            putBoolean("monday", timing.monday)
            putString("mondayOpen", timing.mondayOpen)
            putString("mondayClose", timing.mondayClose)


            putBoolean("tuesday", timing.tuesday)
            putString("tuesdayOpen", timing.tuesdayOpen)
            putString("tuesdayClose", timing.tuesdayClose)

            putBoolean("wednesday", timing.wednesday)
            putString("wednesdayOpen", timing.wednesdayOpen)
            putString("wednesdayClose", timing.wednesdayClose)

            putBoolean("thursday", timing.thursday)
            putString("thursdayOpen", timing.thursdayOpen)
            putString("thursdayClose", timing.thursdayClose)

            putBoolean("friday", timing.friday)
            putString("fridayOpen", timing.fridayOpen)
            putString("fridayClose", timing.fridayClose)

            putBoolean("saturday", timing.saturday)
            putString("saturdayOpen", timing.saturdayOpen)
            putString("saturdayClose", timing.saturdayClose)

            putBoolean("sunday", timing.sunday)
            putString("sundayOpen", timing.sundayOpen)
            putString("sundayClose", timing.sundayClose)
            apply()
        }

    }

    fun getStoreTiming(): StoreTime {
        val storeTime = StoreTime()
        storeTime.monday = preferences.getBoolean("monday", false)
        storeTime.mondayOpen = preferences.getString("mondayOpen", "") ?: ""
        storeTime.mondayClose = preferences.getString("mondayClose", "") ?: ""

        storeTime.tuesday = preferences.getBoolean("tuesday", false)
        storeTime.tuesdayOpen = preferences.getString("tuesdayOpen", "") ?: ""
        storeTime.tuesdayClose = preferences.getString("tuesdayClose", "") ?: ""

        storeTime.wednesday = preferences.getBoolean("wednesday", false)
        storeTime.wednesdayOpen = preferences.getString("wednesdayOpen", "") ?: ""
        storeTime.wednesdayClose = preferences.getString("wednesdayClose", "") ?: ""

        storeTime.thursday = preferences.getBoolean("thursday", false)
        storeTime.thursdayOpen = preferences.getString("thursdayOpen", "") ?: ""
        storeTime.thursdayClose = preferences.getString("thursdayClose", "") ?: ""

        storeTime.friday = preferences.getBoolean("friday", false)
        storeTime.fridayOpen = preferences.getString("fridayOpen", "") ?: ""
        storeTime.fridayClose = preferences.getString("fridayClose", "") ?: ""

        storeTime.saturday = preferences.getBoolean("saturday", false)
        storeTime.saturdayOpen = preferences.getString("saturdayOpen", "") ?: ""
        storeTime.saturdayClose = preferences.getString("saturdayClose", "") ?: ""

        storeTime.sunday = preferences.getBoolean("sunday", false)
        storeTime.sundayOpen = preferences.getString("sundayOpen", "") ?: ""
        storeTime.sundayClose = preferences.getString("sundayClose", "") ?: ""

        return storeTime
    }

    private fun getArea(): Area {
        return Area(preferences.getInt("area_id", 0),
              preferences.getString("area_name", "") ?: "",
                    preferences.getInt("area_city", 0),
                    preferences.getInt("area_pincode", 0)
        )
    }

    private fun getCity(): City {
        return City(preferences.getInt("city_id", 0),
            preferences.getString("city_name", "") ?: "",
            preferences.getInt("city_state", 0),
        )
    }

    private fun getState(): State {
        return State(preferences.getInt("state_id", 0),
            preferences.getString("state_name", "") ?: "",
            preferences.getInt("state_country", 0),
        )
    }

    private fun getCountry(): Country {
        return Country(preferences.getInt("country_id", 0),
            preferences.getString("country_name", "") ?: "",
        )
    }


    fun getSellerLogo(): String = preferences.getString("logo","")!!

    fun saveTokens(accessToken: String, refreshToken: String) {

        preferences.edit().apply {
            putString("accessToken", accessToken)
            putString("refreshToken", refreshToken)
            apply()
        }

    }

    fun getAccessToken(): String = preferences.getString("accessToken", "")!!

    fun logOut() {
        preferences.edit().apply {
            clear()
            apply()
        }
    }

    fun getBusinessName(): String = preferences.getString("businessName", "")!!

    fun getScreenTimeout(): Int = preferences.getInt("screen_timeout", 15)

    fun setScreenTimeout(time: Int) {
        preferences.edit().apply {
            putInt("screen_timeout", time)
            apply()
        }

    }
}