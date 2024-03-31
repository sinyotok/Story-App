package com.aryanto.storyapp.ui.core.data.remote.reponse

import android.os.Message
import com.aryanto.storyapp.ui.core.data.model.Story

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<Story>
)
