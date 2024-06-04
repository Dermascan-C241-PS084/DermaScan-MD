package com.project.bangkit.dermascan.ui.profil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.bangkit.dermascan.data.pref.UserPreference
import com.project.bangkit.dermascan.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = UserPreference.getInstance(application.dataStore)

    val user = preferences.getSession().asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    fun updateProfile(newName: String, newEmail: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val user = preferences.getSession().first()
            if (newName != user.name || newEmail != user.token) {
                val updatedUser = user.copy(name = newName, email = newEmail)
                preferences.saveSession(updatedUser)
                _statusMessage.value = "Update successful"
                _isSaved.value = true
            } else {
                _statusMessage.value = "New data must be different from the old data"
                _isSaved.value = false
            }
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferences.logout()
        }
    }
}