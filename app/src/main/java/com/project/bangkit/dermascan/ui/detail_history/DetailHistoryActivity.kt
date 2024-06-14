package com.project.bangkit.dermascan.ui.detail_history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.bangkit.dermascan.data.pref.DetectionResult
import com.project.bangkit.dermascan.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detectionResult = intent.getSerializableExtra("DETECTION_RESULT") as? DetectionResult

        // Now you can use detectionResult to populate your views
        binding.tvResult.text = detectionResult?.result
        binding.tvAdvice.text = detectionResult?.advice
        binding.tvTimestamp.text = detectionResult?.createdAt

        if (!detectionResult?.imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(detectionResult?.imageUrl)
                .into(binding.ivDetailHistory)
        }

        binding.btnBackDetailHistory.setOnClickListener {
            finish()
        }
    }
}