package com.project.bangkit.dermascan.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityArticle2Binding

class Article2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityArticle2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticle2Binding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}