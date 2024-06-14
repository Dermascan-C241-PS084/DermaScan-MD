package com.project.bangkit.dermascan.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityArticle1Binding

class Article1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityArticle1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticle1Binding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}