package com.latifaumunyana.multimedia.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.viewModels
import com.latifaumunyana.multimedia.R
import com.latifaumunyana.multimedia.databinding.ActivityLoginBinding
import com.latifaumunyana.multimedia.model.LoginRequest
import com.latifaumunyana.multimedia.model.LoginResponse
import com.latifaumunyana.multimedia.utils.Constants
import com.latifaumunyana.multimedia.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        redirectUser()
    }

    fun redirectUser(){
        val sharedPreferences = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(Constants.ACCESS_TOKEN, "")

        if (token!!.isNotBlank()){
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        binding.btnLogin.setOnClickListener{ validateLogin() }
        loginViewModel.errorLiveData.observe(this){error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        loginViewModel.loginLiveData.observe(this){loginResponse ->
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
            persistLogin(loginResponse)
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun persistLogin(loginResponse: LoginResponse){
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPreferences = getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.FIRST_NAME, loginResponse.firstname)
        editor.putString(Constants.LAST_NAME, loginResponse.lastname)
        editor.putString(Constants.ACCESS_TOKEN, loginResponse.accessToken)
        editor.apply()
    }

    private fun validateLogin(){
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        var error = false

        if (username.isBlank()){
            binding.tilUsername.error = "Username Required"
            error = true
        }

        if (password.isBlank()){
            binding.tilPassword.error = "Password Required"
            error = true
        }

        if (!error){
            val loginRequest = LoginRequest(username,password)
            loginViewModel.login(loginRequest)
        }
    }
}