package com.aryanto.storyapp.ui.core.data.remote.network

import com.aryanto.storyapp.ui.core.data.remote.reponse.DetailResponse
import com.aryanto.storyapp.ui.core.data.remote.reponse.LoginResponse
import com.aryanto.storyapp.ui.core.data.remote.reponse.RegisterResponse
import com.aryanto.storyapp.ui.core.data.remote.reponse.StoryResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int?,
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetail(
        @Path("id") id: String
    ): DetailResponse

}