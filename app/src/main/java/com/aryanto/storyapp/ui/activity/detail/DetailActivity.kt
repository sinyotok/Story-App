package com.aryanto.storyapp.ui.activity.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.storyapp.R
import com.aryanto.storyapp.databinding.ActivityDetailBinding
import com.aryanto.storyapp.ui.core.data.model.Story
import com.aryanto.storyapp.ui.utils.ClientState
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailVM: DetailVM by viewModel<DetailVM>()
    private lateinit var storyId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val story: Story? = intent.getParcelableExtra("user")
        if (story != null) {
            storyId = story.id
            handleDetail(story)
        }

        detailVM.detail(storyId)

        setView()

    }

    private fun setView() {
        binding.apply {
            detailVM.detail.observe(this@DetailActivity) { resources ->
                when (resources) {
                    is ClientState.Success -> {
                        detailProgressBar.visibility = View.GONE
                        resources.data?.let { handleDetail(it) }
                    }

                    is ClientState.Error -> {
                        detailProgressBar.visibility = View.GONE
                        showToast("${resources.message}")
                    }

                    is ClientState.Loading -> {
                        detailProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleDetail(detailView: Story) {
        binding.apply {
            detailName.text = detailView.name
            detailCreatedAt.text = detailView.createdAt
            detailDescription.text = detailView.description

            Glide.with(this@DetailActivity)
                .load(detailView.photoUrl)
                .into(detailImage)

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DetailActivity, message, Toast.LENGTH_LONG).show()
        Log.e("SA-DA Toast", message)
    }

}