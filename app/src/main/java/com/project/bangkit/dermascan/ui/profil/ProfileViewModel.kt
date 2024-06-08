package com.project.bangkit.dermascan.ui.profil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.bangkit.dermascan.config.ApiConfig
import com.project.bangkit.dermascan.data.pref.UserModel
import com.project.bangkit.dermascan.data.pref.UserPreference
import com.project.bangkit.dermascan.data.pref.dataStore
import com.project.bangkit.dermascan.request.RequestEditProfile
import com.project.bangkit.dermascan.response.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = UserPreference.getInstance(application.dataStore)

    val user = preferences.getSession().asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    private val _editResponse = MutableLiveData<RegisterResponse?>()
    val editResponse: LiveData<RegisterResponse?> = _editResponse


    fun editProfile(userId: String, requestEditProfile: RequestEditProfile) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().editProfile(userId, requestEditProfile.name, requestEditProfile.password)
        api.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _editResponse.value = response.body()
                    val newUser = UserModel(userId, requestEditProfile.name, requestEditProfile.password, user.value?.email ?: "", user.value?.token ?: "")
                    viewModelScope.launch {
                        preferences.saveSession(newUser)
                    }
                } else {
                    _editResponse.postValue(null)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _editResponse.value = null
            }
        })
    }
    fun changePassword(userId: String, requestEditProfile: RequestEditProfile) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().changePassword(userId,requestEditProfile.password)
        api.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _editResponse.value = response.body()
                    val newUser = UserModel(userId, user.value?.name ?: "", user.value?.email ?: "", requestEditProfile.password, user.value?.token ?: "")
                    viewModelScope.launch {
                        preferences.saveSession(newUser)
                    }
                } else {
                    _editResponse.postValue(null)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _editResponse.value = null
            }
        })
    }

    fun logout() {
        viewModelScope.launch {
            preferences.logout()
        }
    }
}