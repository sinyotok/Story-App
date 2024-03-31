package com.aryanto.storyapp.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.storyapp.R
import com.aryanto.storyapp.databinding.ActivityHomeBinding
import com.aryanto.storyapp.ui.activity.auth.login.LoginActivity
import com.aryanto.storyapp.ui.utils.ClientState
import com.aryanto.storyapp.ui.utils.TokenManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewAdapter: HomeAdapter

    private val homeVM: HomeVM by viewModel<HomeVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.homeToolbar)

        homeVM.getStories()

        setAdapter()
        setView()

    }

    private fun setAdapter() {
        binding.apply {
            viewAdapter = HomeAdapter(listOf())
            homeListItem.layoutManager = LinearLayoutManager(this@HomeActivity)
            homeListItem.adapter = viewAdapter
        }
    }

    private fun setView() {
        binding.apply {
            homeVM.stories.observe(this@HomeActivity) { resources ->
                when (resources) {
                    is ClientState.Success -> {
                        homeProgressBar.visibility = View.GONE
                        resources.data?.let { viewAdapter.updateItem(it) }
                    }

                    is ClientState.Error -> {
                        homeProgressBar.visibility = View.GONE
                        showToast("${resources.message}")
                    }

                    is ClientState.Loading -> {
                        homeProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                lifecycleScope.launch {
                    val tokenManager = TokenManager.getInstance(this@HomeActivity)
                    tokenManager.clearTokenAndSession()
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_LONG).show()
        Log.e("SA-HA Toast", message)
    }

}