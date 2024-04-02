package com.aryanto.storyapp.ui.di

import com.aryanto.storyapp.ui.activity.auth.login.LoginVM
import com.aryanto.storyapp.ui.activity.auth.register.RegisterVM
import com.aryanto.storyapp.ui.activity.detail.DetailVM
import com.aryanto.storyapp.ui.activity.home.HomeVM
import com.aryanto.storyapp.ui.activity.upload.UploadVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginVM(get()) }
    viewModel { RegisterVM(get()) }
    viewModel { HomeVM(get()) }
    viewModel { DetailVM(get()) }
    viewModel { UploadVM(get()) }
}