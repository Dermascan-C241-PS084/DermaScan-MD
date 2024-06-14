package com.project.bangkit.dermascan.data.pref

import java.io.Serializable

data class DetectionResult(
    val imageUrl: String,
    val result: String,
    val createdAt: String,
    val advice: String
) : Serializable

