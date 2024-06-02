package com.project.bangkit.dermascan.ui.detail_history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackDetailHistory.setOnClickListener {
         finish()

        }
    }

}