package com.project.bangkit.dermascan.di

import android.content.Context
import com.project.bangkit.dermascan.data.UserRepository
import com.project.bangkit.dermascan.data.pref.UserPreference
import com.project.bangkit.dermascan.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}