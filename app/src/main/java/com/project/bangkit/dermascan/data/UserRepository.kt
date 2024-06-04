package com.project.bangkit.dermascan.data

import com.project.bangkit.dermascan.data.pref.UserModel
import com.project.bangkit.dermascan.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(

    private val userPreference: UserPreference
){
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getToken(): Flow<String> {
        return userPreference.getToken()
    }

    fun isLoggedIn(): Flow<Boolean> {
        return userPreference.isLoggedIn()
    }

    suspend fun logout() {
        userPreference.logout()
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}