package com.project.bangkit.dermascan.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityArticle5Binding

class Article5Activity : AppCompatActivity() {
    private lateinit var binding: ActivityArticle5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticle5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackArtikel5.setOnClickListener {
            finish()  // Finish current activity
        }
    }
}