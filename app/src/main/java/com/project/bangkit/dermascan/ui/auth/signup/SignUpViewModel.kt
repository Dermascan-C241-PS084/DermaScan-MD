package com.project.bangkit.dermascan.ui.auth.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.bangkit.dermascan.config.ApiConfig
import com.project.bangkit.dermascan.request.RequestRegister
import com.project.bangkit.dermascan.response.RegisterResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val _response = MutableLiveData<RegisterResponse?>()
    val response: LiveData<RegisterResponse?> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val request = RequestRegister(name, email, password)
        val client = ApiConfig.getApiService().register(request)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _response.value = response.body()
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val jsonObject = JSONObject(errorBody)
                        val errorMessage = jsonObject.getString("error")
                        _errorMessage.value = errorMessage
                    } else {
                        _errorMessage.value = "Registration failed: ${response.message()}"
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                if (t.message?.contains("Unable to resolve host") == true) {
                    _errorMessage.value = "No connection. Check your internet connection!"
                } else {
                    _errorMessage.value = "Registration failed: ${t.message.toString()}"
                }
                Log.e("Register User", "Failure: ${t.message.toString()}")
            }
        })
    }
}