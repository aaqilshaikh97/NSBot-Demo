package com.nsbot.demoapp.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nsbot.demoapp.R
import com.nsbot.demoapp.databinding.ActivityHomeBinding
import com.nsbot.demoapp.ui.viewModel.MainViewModel

class HomeActivity: AppCompatActivity() {

    private var currentScreen = 0
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    private val timeoutHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.init(this)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentScreen = destination.id
            Log.e(TAG,"navController: screen ${destination.navigatorName} | ${destination.label} | ${destination.id}")
        }

    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        stopTimer()
        super.onPause()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        restartTimer()
    }

    private fun onTimeout() {
        Log.e(TAG,"onTimeout")
        if (currentScreen == R.id.videoPlayerFragment) {
            return
        }
        navController.navigate(R.id.videoPlayerFragment)
    }

    private fun startTimer() {
        //convert time in seconds to milliseconds
        Log.d(TAG,"startTimer")
        val timer = mainViewModel.appDataHelper.getScreenTimeout() * 1000

        timeoutHandler.postDelayed({
            onTimeout()
        }, timer.toLong())
    }

    private fun stopTimer() {
        //rest timer
        Log.d(TAG,"stopTimer")
        timeoutHandler.removeCallbacksAndMessages(null)
    }

    private fun restartTimer() {
        Log.e(TAG,"restartTimer")
        stopTimer()
        startTimer()
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}