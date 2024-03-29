package com.aryanto.storyapp.ui.activity.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.storyapp.R
import com.aryanto.storyapp.databinding.ActivityRegisterBinding
import com.aryanto.storyapp.ui.activity.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setRegisterBtn()
        setPageLogin()

    }

    private fun setRegisterBtn() {
        binding.apply {
            btnSubmitRegister.setOnClickListener {
                if (validateInput()) {
                    showLoading(true)
                    performRegister()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        binding.apply {
            var isValid = true

            if (nameEdtRegister.text.isNullOrEmpty()) {
                nameTiLayout.error = "Nama tidak boleh kosong"
                isValid = false
            } else {
                nameTiLayout.error = null
            }

            if (emailEdtRegister.text.isNullOrEmpty()) {
                emailTiLayout.error = "Email tidak boleh kosong"
                isValid = false
            } else {
                emailTiLayout.error = null
            }

            if (passwordEdtRegister.text.isNullOrEmpty()) {
                passwordTiLayout.error = "Password tidak boleh kosong"
                isValid = false
            } else {
                passwordTiLayout.error = null
            }

            return isValid
        }
    }

    private fun performRegister() {
        binding.apply {
            btnSubmitRegister.postDelayed({
                showLoading(false)
                Toast.makeText(this@RegisterActivity, "Register berhasil", Toast.LENGTH_SHORT).show()
            }, 2000)
        }
    }

    private fun setPageLogin() {
        binding.apply {
            tvLoginHere.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.apply {
            progressBarRegister.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

}