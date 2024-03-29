package com.aryanto.storyapp.ui.activity.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.storyapp.R
import com.aryanto.storyapp.databinding.ActivityLoginBinding
import com.aryanto.storyapp.ui.activity.auth.register.RegisterActivity
import com.aryanto.storyapp.ui.activity.home.HomeActivity
import com.aryanto.storyapp.ui.core.data.model.LoginResult
import com.aryanto.storyapp.ui.utils.ClientState
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginVM: LoginVM by viewModel<LoginVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setView()
        setLoginBtn()
        setPageRegister()

    }

    private fun setView() {
        binding.apply {
            loginVM.loginResult.observe(this@LoginActivity) { resources ->
                when (resources) {
                    is ClientState.Success -> {
                        progressBarLogin.visibility = View.GONE
                        resources.data?.let { handleLoginSuccess(it) }
                    }

                    is ClientState.Error -> {
                        handleError(resources.message)
                        progressBarLogin.visibility = View.GONE
                    }

                    is ClientState.Loading -> {
                        progressBarLogin.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleError(errorMSG: String?) {
        binding.apply {
            when {
                errorMSG?.contains("email") == true -> {
                    emailTiLayout.error = errorMSG
                }

                errorMSG?.contains("password") == true -> {
                    passwordTiLayout.error = errorMSG
                }

                else ->{
                    showToast("$errorMSG")
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResult: LoginResult) {
        val auth = loginResult.token
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setLoginBtn() {
        binding.apply {
            btnSubmitLogin.setOnClickListener {
                val email = emailEdtLogin.text.toString()
                val pass = passwordEdtLogin.text.toString()
                loginVM.performLogin(email, pass)
            }
        }
    }

    private fun setPageRegister() {
        binding.apply {
            tvRegisterHere.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


}