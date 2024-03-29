package com.aryanto.storyapp.ui.di

import com.aryanto.storyapp.ui.activity.auth.login.LoginVM
import com.aryanto.storyapp.ui.activity.auth.register.RegisterVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginVM(get()) }
    viewModel { RegisterVM(get()) }
}