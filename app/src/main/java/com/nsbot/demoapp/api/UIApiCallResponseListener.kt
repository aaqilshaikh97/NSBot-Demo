package com.nsbot.demoapp.api

import androidx.annotation.MainThread

interface UIApiCallResponseListener {

    @MainThread
    fun onFailed(msg: String)

    @MainThread
    fun onSuccess(msg: String)

}

