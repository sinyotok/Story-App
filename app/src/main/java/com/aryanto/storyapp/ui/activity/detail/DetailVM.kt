package com.aryanto.storyapp.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyapp.ui.core.data.model.Story
import com.aryanto.storyapp.ui.core.data.remote.network.ApiService
import com.aryanto.storyapp.ui.core.data.remote.reponse.DetailResponse
import com.aryanto.storyapp.ui.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailVM(
    private val apiService: ApiService
) : ViewModel() {

    private val _detail = MutableLiveData<ClientState<Story>>()
    val detail: LiveData<ClientState<Story>> = _detail

    fun detail(storyId: String) {
        viewModelScope.launch {
            try {
                _detail.postValue(ClientState.Loading())
                val response = apiService.getDetail(storyId)

                if (response.error) {
                    _detail.postValue(ClientState.Error(response.message))
                } else {
                    _detail.postValue(ClientState.Success(response.story))
                }

            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _detail.postValue(ClientState.Error(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val jsonString = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(jsonString, DetailResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _detail.postValue(ClientState.Error(errorMSG))
        } catch (e: Exception) {
            _detail.postValue(ClientState.Error("Error when parsing response msg", null))
        }

    }

}