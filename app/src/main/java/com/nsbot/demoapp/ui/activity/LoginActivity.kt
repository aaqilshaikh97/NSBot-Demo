package com.nsbot.demoapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nsbot.demoapp.api.UIApiCallResponseListener
import com.nsbot.demoapp.databinding.ActivityLoginBinding
import com.nsbot.demoapp.ui.viewModel.LoginViewModel

class LoginActivity: AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private val uiApiCallResponseListener = object : UIApiCallResponseListener {
        override fun onFailed(msg: String) {
            Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
            binding.btnSingIn.isEnabled = true
            binding.progressBar.visibility = View.INVISIBLE
            binding.tvError.text = msg
        }

        override fun onSuccess(msg: String) {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.init(this)

        binding.btnSingIn.setOnClickListener {

            val phone = binding.etPhone.text.toString().trim()
            if (phone.isEmpty()) {
                binding.textPhone.error = "Enter mobile number"
                return@setOnClickListener
            }
            if (phone.length != 10) {
                binding.textPhone.error = "Enter 10 digit mobile number"
                return@setOnClickListener
            }

            val password = binding.etPassword.text.toString().trim()
            if (password.isEmpty()) {
                binding.textPhone.error = "Enter password"
                return@setOnClickListener
            }
            if (password.length != 6) {
                binding.textPhone.error = "Must contain min 6 characters"
                return@setOnClickListener
            }

            binding.btnSingIn.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
            loginViewModel.login("+91$phone", password, uiApiCallResponseListener)

        }

    }


}