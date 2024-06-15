package com.project.bangkit.dermascan.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityArticle3Binding

class Article3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityArticle3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticle3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackArtikel3.setOnClickListener {
            finish()  // Finish current activity
        }
    }
}