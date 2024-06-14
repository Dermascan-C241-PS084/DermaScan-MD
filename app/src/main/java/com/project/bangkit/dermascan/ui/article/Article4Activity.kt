package com.project.bangkit.dermascan.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityArticle4Binding

class Article4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityArticle4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticle4Binding.inflate(layoutInflater)
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}