package com.aryanto.storyapp.ui.core.data.remote.network

import com.aryanto.storyapp.ui.core.data.remote.reponse.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}