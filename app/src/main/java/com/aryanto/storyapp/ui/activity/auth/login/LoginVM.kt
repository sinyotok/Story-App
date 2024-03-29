package com.aryanto.storyapp.ui.activity.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyapp.ui.core.data.model.LoginResult
import com.aryanto.storyapp.ui.core.data.remote.network.ApiService
import com.aryanto.storyapp.ui.core.data.remote.reponse.LoginResponse
import com.aryanto.storyapp.ui.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _loginResult = MutableLiveData<ClientState<LoginResult>>()
    val loginResult: LiveData<ClientState<LoginResult>> = _loginResult


    fun performLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginResult.postValue(ClientState.Loading())
                val response = apiService.login(email, password)

                if (response.error) {
                    _loginResult.postValue(ClientState.Error(response.message))
                } else {
                    _loginResult.postValue(ClientState.Success(response.loginResult!!))
                }
            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _loginResult.postValue(ClientState.Error(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val errorBody = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _loginResult.postValue(ClientState.Error(errorMSG))
        } catch (e: Exception) {
            _loginResult.postValue(ClientState.Error("Error when parsing response msg", null))
        }

    }

}