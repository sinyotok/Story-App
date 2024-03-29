package com.aryanto.storyapp.ui.di

import com.aryanto.storyapp.ui.core.data.remote.network.ApiClient
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
}