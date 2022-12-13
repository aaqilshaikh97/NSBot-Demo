package com.nsbot.demoapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import com.nsbot.demoapp.R
import com.nsbot.demoapp.api.UIApiCallResponseListener
import com.nsbot.demoapp.ui.viewModel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    private val uiApiCallResponseListener = object : UIApiCallResponseListener {

        override fun onFailed(msg: String) {
            Toast.makeText(this@SplashActivity, msg, Toast.LENGTH_LONG).show()
        }

        override fun onSuccess(msg: String) {
            Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.init(this)

        //check if access token exists, to know if user login or not
        if (splashViewModel.appDataHelper.getAccessToken().isEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }else {
            splashViewModel.getProfile(uiApiCallResponseListener)
        }

    }

}