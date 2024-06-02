package com.project.bangkit.dermascan.ui.detection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}