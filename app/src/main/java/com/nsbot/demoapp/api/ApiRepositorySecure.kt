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
import retrofit2.HttpException

class ApiRepositorySecure(context: Context) {

    companion object {
        private const val TAG = "ApiRepositorySecure"
    }

    private val compositeDisposable = CompositeDisposable()
    private val dataHelper = AppDataHelper(context)

    private val apiInterface = RetrofitClientSecure(dataHelper.getAccessToken()).getRetrofitInstance().create(ApiInterface::class.java)

    fun loadProfile(uiApiCallResponseListener: UIApiCallResponseListener? = null) {

        compositeDisposable.add(
            apiInterface.loadProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val response = it.string().trim()
                    Log.e(TAG, "fetchData > $response")
                    try {
                        val data = JSONObject(response)

                        val accountData = data.getJSONObject("account")
                        val sellerData = data.getJSONObject("seller")

                        //convert json into java object
                        val sellerObj: Seller = Gson().fromJson(sellerData.toString(), Seller::class.java)
                        val accountObj: Account = Gson().fromJson(accountData.toString(), Account::class.java)

                        if (!accountObj.isActive) {
                            uiApiCallResponseListener?.onFailed("Your account is disabled, please contact our team for more information")
                            return@subscribe
                        }
                        Log.e("save","business ${sellerObj.businessName}")
                        dataHelper.saveSeller(sellerObj)
                        dataHelper.saveAccount(accountObj)

                        uiApiCallResponseListener?.onSuccess("Sign In success")
                        return@subscribe
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    uiApiCallResponseListener?.onFailed("Error")

                }, {

                    if (it is HttpException) {
                        uiApiCallResponseListener?.onFailed("Something went wrong")
                        return@subscribe
                    }

                    uiApiCallResponseListener?.onFailed("Something went wrong")
                    it.printStackTrace()
                })
        )

    }


}





