package com.nsbot.demoapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.nsbot.demoapp.api.ApiRepositorySecure
import com.nsbot.demoapp.api.UIApiCallResponseListener
import com.nsbot.demoapp.config.AppDataHelper

class SplashViewModel: ViewModel() {

    lateinit var appDataHelper: AppDataHelper
    private lateinit var apiRepositorySecure: ApiRepositorySecure

    fun init(context: Context) {
        appDataHelper = AppDataHelper(context)
        apiRepositorySecure = ApiRepositorySecure(context)
    }

    fun getProfile(uiApiCallResponseListener: UIApiCallResponseListener) {
        apiRepositorySecure.loadProfile(uiApiCallResponseListener)
    }

}