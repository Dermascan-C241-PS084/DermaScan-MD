package com.project.bangkit.dermascan.request


data class RequestRegister(

	val name: String,
	val email: String,
	val password: String
)

data class RequestLogin(

	val email: String,
	val password: String
)

data class RequestEditProfile(

	val name: String,
	val password: String
)

