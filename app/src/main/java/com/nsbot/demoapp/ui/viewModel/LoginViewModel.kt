package com.nsbot.demoapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.nsbot.demoapp.api.ApiRepositorySecure
import com.nsbot.demoapp.api.ApiRepositoryUnsecure
import com.nsbot.demoapp.api.UIApiCallResponseListener
import com.nsbot.demoapp.config.AppDataHelper

class LoginViewModel: ViewModel() {

    lateinit var appDataHelper: AppDataHelper
    private lateinit var apiRepositoryUnsecure: ApiRepositoryUnsecure

    fun init(context: Context) {
        appDataHelper = AppDataHelper(context)
        apiRepositoryUnsecure = ApiRepositoryUnsecure(context)
    }

    fun login(phone: String, password: String, uiApiCallResponseListener: UIApiCallResponseListener) {
        apiRepositoryUnsecure.signIn(phone, password, uiApiCallResponseListener)
    }

}