package com.aryanto.storyapp.ui.di

import com.aryanto.storyapp.ui.core.data.remote.network.ApiClient
import com.aryanto.storyapp.ui.utils.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
//    single { TokenManager.getInstance(androidContext()) }
}