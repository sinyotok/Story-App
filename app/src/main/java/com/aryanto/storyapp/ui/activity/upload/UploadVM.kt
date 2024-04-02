package com.aryanto.storyapp.ui.activity.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryanto.storyapp.ui.core.data.remote.network.ApiService
import com.aryanto.storyapp.ui.core.data.remote.reponse.AddStoryResponse
import com.aryanto.storyapp.ui.utils.ClientState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class UploadVM(
    private val apiService: ApiService
) : ViewModel() {
    private val _addStory = MutableLiveData<ClientState<AddStoryResponse>>()
    val addStory: LiveData<ClientState<AddStoryResponse>> = _addStory

    private val _description = MutableLiveData<String?>()
    val description: LiveData<String?> = _description

    private val _photoPath = MutableLiveData<Uri>()
    val photoPath: LiveData<Uri> = _photoPath

    fun setPhotoPath(uri: Uri) {
        _photoPath.postValue(uri)
    }

    fun uploadStory(
        photoPart: MultipartBody.Part,
        descPart: RequestBody,
//        latPart: Double,
//        lonPart: Double
    ) {
        viewModelScope.launch {
            try {
                _addStory.postValue(ClientState.Loading())
                val response = apiService.uploadStory(photoPart, descPart)

                if (response.error) {
                    _addStory.postValue(ClientState.Error(response.message))
                } else {
                    _addStory.postValue(ClientState.Success(response))
                }

            } catch (he: HttpException) {
                handleHttpException(he)
            } catch (e: Exception) {
                val errorMSG = when (e) {
                    is IOException -> "${e.message}"
                    else -> "Unknown error: ${e.message}"
                }
                _addStory.postValue(ClientState.Error(errorMSG))
            }
        }
    }

    private fun handleHttpException(he: HttpException) {
        val jsonString = he.response()?.errorBody()?.string()
        try {
            val errorResponse = Gson().fromJson(jsonString, AddStoryResponse::class.java)
            val errorMSG = errorResponse?.message ?: "Unknown error"
            _addStory.postValue(ClientState.Error(errorMSG))
        } catch (e: Exception) {
            _addStory.postValue(ClientState.Error("Error when parsing response msg", null))
        }

    }

}