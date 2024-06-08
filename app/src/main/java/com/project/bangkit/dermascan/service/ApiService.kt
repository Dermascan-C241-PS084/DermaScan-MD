package com.project.bangkit.dermascan.service


import com.project.bangkit.dermascan.request.RequestLogin
import com.project.bangkit.dermascan.request.RequestRegister
import com.project.bangkit.dermascan.response.LoginResponse
import com.project.bangkit.dermascan.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {


    @POST("/register")
    fun register(@Body post: RequestRegister,
                 ): Call<RegisterResponse>

    @POST("/login")
    fun login(
        @Body post: RequestLogin,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @PUT("/editUsers/{userId}")
    fun editProfile(
        @Path("userId") userId: String,
        @Field("name") name: String,
        @Field("email") email: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @PUT("/editUsers/{userId}")
    fun changePassword(
        @Path("userId") userId: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>


}