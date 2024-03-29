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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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

        setLoginBtn()
        setPageRegister()

    }

    private fun setLoginBtn() {
        binding.apply {
            btnSubmitLogin.setOnClickListener {
                if (validateInput()) {
                    showLoading(true)
                    performLogin()
                }
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

    private fun validateInput(): Boolean {
        binding.apply {
            var isValid = true

            if (emailEdtLogin.text.isNullOrEmpty()) {
                emailTiLayout.error = "Email tidak boleh kosong!"
                isValid = false
            } else {
                emailTiLayout.error = null
            }

            if (passwordEdtLogin.text.isNullOrEmpty()) {
                passwordTiLayout.error = "Password tidak boleh kosong!"
                isValid = false
            } else {
                passwordTiLayout.error = null
            }
            return isValid
        }
    }

    private fun performLogin() {
        binding.apply {
            btnSubmitLogin.postDelayed({
                showLoading(false)
                Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, HomeActivity::class.java)
//        startActivity(intent)
            }, 2000)
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.apply {
            progressBarLogin.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

}