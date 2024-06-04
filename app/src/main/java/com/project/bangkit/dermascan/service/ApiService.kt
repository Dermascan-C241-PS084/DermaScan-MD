package com.project.bangkit.dermascan.service


import com.project.bangkit.dermascan.request.RequestLogin
import com.project.bangkit.dermascan.request.RequestRegister
import com.project.bangkit.dermascan.response.LoginResponse
import com.project.bangkit.dermascan.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("/register")
    fun register(@Body post: RequestRegister,
                 ): Call<RegisterResponse>

    @POST("/login")
    fun login(
        @Body post: RequestLogin,
    ): Call<LoginResponse>
}