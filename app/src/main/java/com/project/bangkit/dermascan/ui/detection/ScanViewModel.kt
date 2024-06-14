package com.project.bangkit.dermascan.ui.detection

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.bangkit.dermascan.config.ApiConfig
import com.project.bangkit.dermascan.data.pref.DetectionResult
import com.project.bangkit.dermascan.response.ResponseUploadImage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanViewModel : ViewModel() {
    val uploadImageResponse = MutableLiveData<ResponseUploadImage>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    var currentImageUri: Uri? = null
    val detectionResults = MutableLiveData<MutableList<DetectionResult>>()

    fun uploadImage(multipartBody: MultipartBody.Part) {
        isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val call = apiService.uploadImage(multipartBody)
        call.enqueue(object : Callback<ResponseUploadImage> {
            override fun onResponse(call: Call<ResponseUploadImage>, response: Response<ResponseUploadImage>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    uploadImageResponse.value = response.body()
                } else {
                    errorMessage.value = "Error: ${response.errorBody()}"
                    Log.e("Upload Image", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseUploadImage>, t: Throwable) {
                isLoading.value = false
                if (t.localizedMessage?.contains("Unable to resolve host") == true) {
                    errorMessage.value = "Check Your Connection"
                } else {
                    errorMessage.value = "Connection error: ${t.localizedMessage}"
                }
                Log.e("Upload Image", "Connection error: ${t.localizedMessage}")
            }
        })
    }
}