package com.nsbot.demoapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.nsbot.demoapp.config.AppDataHelper

class MainViewModel: ViewModel() {

    lateinit var appDataHelper: AppDataHelper

    fun init(context: Context) {
        appDataHelper = AppDataHelper(context)
    }

}