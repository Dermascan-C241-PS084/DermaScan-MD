package com.project.bangkit.dermascan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityAboutUsBinding

class AboutUsActivity: AppCompatActivity(){
    private lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackProfile.setOnClickListener {
            finish()
        }

        }

    }
