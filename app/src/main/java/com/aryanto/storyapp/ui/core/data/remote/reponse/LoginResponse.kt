package com.aryanto.storyapp.ui.core.data.remote.reponse

import com.aryanto.storyapp.ui.core.data.model.LoginResult

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)
