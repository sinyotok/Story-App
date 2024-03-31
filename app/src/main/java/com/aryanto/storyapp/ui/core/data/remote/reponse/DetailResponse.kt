package com.aryanto.storyapp.ui.core.data.remote.reponse

import com.aryanto.storyapp.ui.core.data.model.Story

data class DetailResponse(
    val error: Boolean,
    val message: String,
    val story: Story
)
