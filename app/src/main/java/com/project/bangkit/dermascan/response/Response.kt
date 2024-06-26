package com.project.bangkit.dermascan.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("token")
    val token: String
)

data class EditResponse(

    @field:SerializedName("result")
    val result: Result,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class Result(

    @field:SerializedName("message")
    val message: String
)

data class ResponseUploadImage(
    val data: Data,
    val message: String
)

data class Data(
    val result: String,
    val createdAt: String,
    val confidenceScore: Any,
    val isAboveThreshold: Boolean,
    val imageUrl: String,
    val id: String
) : Serializable

