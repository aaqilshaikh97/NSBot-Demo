package com.nsbot.demoapp.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nsbot.demoapp.config.AppDataHelper
import com.nsbot.demoapp.models.Account
import com.nsbot.demoapp.models.Seller
import com.nsbot.demoapp.models.StoreTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class ApiRepositoryUnsecure(context: Context) {

    companion object {
        private const val TAG = "ApiRepository"
    }

    private val compositeDisposable = CompositeDisposable()
    private val apiInterfaceUnsecure = RetrofitClientUnSecure.getRetrofitInstance().create(ApiInterface::class.java)

    private val appDataHelper = AppDataHelper(context)

    fun signIn(
        mobile: String,
        password: String,
        uiApiCallResponseListener: UIApiCallResponseListener
    ) {

        val body = HashMap<String, Any>()
        body["username"] = mobile
        body["password"] = password

        compositeDisposable.add(
            apiInterfaceUnsecure.signIn(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val response = it.string().trim()
                    Log.e(TAG, "fetchData > $response")
                    try {
                        val data = JSONObject(response)

                        val accessToken = data.getString("access")
                        val refreshToken = data.getString("refresh")
                        val accountData = data.getJSONObject("account")
                        val sellerData = data.getJSONObject("seller")

                        val sellerObj: Seller = Gson().fromJson(sellerData.toString(), Seller::class.java)
                        val accountObj: Account = Gson().fromJson(accountData.toString(), Account::class.java)

                        if (!accountObj.isActive) {
                            uiApiCallResponseListener.onFailed("Your account is disabled, please contact our team for more information")
                            return@subscribe
                        }

                        appDataHelper.saveTokens(accessToken, refreshToken)
                        appDataHelper.saveSeller(sellerObj)
                        appDataHelper.saveAccount(accountObj)

                        uiApiCallResponseListener.onSuccess("Sign In success")
                        return@subscribe
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    uiApiCallResponseListener.onFailed("Invalid phone or password")

                }, {
                    uiApiCallResponseListener.onFailed("Invalid phone or password")
                    it.printStackTrace()
                })
        )

    }


}





