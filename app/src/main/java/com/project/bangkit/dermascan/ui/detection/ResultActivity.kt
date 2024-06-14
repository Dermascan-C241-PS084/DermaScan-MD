package com.project.bangkit.dermascan.ui.detection

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.data.pref.DetectionResult
import com.project.bangkit.dermascan.databinding.ActivityResultBinding
import com.project.bangkit.dermascan.response.Data
import com.project.bangkit.dermascan.ui.adapter.HistoryAdapter
import com.project.bangkit.dermascan.ui.history.HistoryFragment
import com.project.bangkit.dermascan.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var detectionResult: DetectionResult
    private val detectionResults = HistoryFragment.detectionResults
    private val adapter = HistoryAdapter(detectionResults)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBackResult.setOnClickListener {
            finish()
        }

        binding.buttonSave.setOnClickListener {
            val data = intent.getSerializableExtra("data") as? Data
            data?.let {
                // Create a new DetectionResult object with the current data
                val detectionResult = DetectionResult(
                    imageUrl = it.imageUrl,
                    result = it.result,
                    createdAt = formatDateToIndonesia(it.createdAt),
                    advice = binding.adviceText.text.toString()
                )

                // Add the DetectionResult object to the list of results
                detectionResults.add(detectionResult)

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()

                // Delay navigation to HistoryFragment for 2 seconds (Toast.LENGTH_SHORT)
                // Delay navigation to HistoryFragment for 2 seconds (Toast.LENGTH_SHORT)

                // Show dialog with button to navigate to HistoryFragment
                AlertDialog.Builder(this)
                    .setMessage("Data saved successfully. Please check your history now, to known detail advice")
                    .setPositiveButton("Close") { _, _ ->
                        // Start MainActivity and navigate to HistoryFragment
                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("destinationId", R.id.navigation_history)
                        }
                        startActivity(intent)
                        finish()
                    }
                    .show()
            }
        }

        // Show loading
        showLoading(true)

        // Get data from Intent
        val data = intent.getSerializableExtra("data") as? Data
        data?.let {
            setData(it)
            // Load image from imageUrl into ImageView
            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.resultImage)
            // Hide loading after data is set
            showLoading(false)
        }
    }


    private fun formatDateToIndonesia(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

    return if (date != null) outputFormat.format(date) else "Invalid date"
}

    private fun setData(data: Data) {
        // Check if confidenceScore is less than 0.2
        if (data.confidenceScore is Double && data.confidenceScore < 0.2) {
            binding.resultText.text = "Normal skin"
        } else {
            binding.resultText.text = data.result
        }

        // Set advice based on result
        val advice = when (data.result) {
            "chickenpox" -> "Chickenpox is a viral infection causing itchy blisters. Rest, stay hydrated, avoid scratching to prevent secondary infections, and isolate infected individuals to prevent spread.\n" +
                    "\n"
            "cellulitis" -> "Cellulitis is a bacterial infection causing redness, swelling, and pain. Keep the infected area clean and take antibiotics as prescribed. Seek medical help immediately if symptoms worsen or fever develops.\n" +
                    "\n"
            "impetigo" -> "Impetigo is a highly contagious skin infection with red, crusted sores. Keep the infected area clean, use antibiotics as directed, avoid sharing personal items, and practice good hygiene.\n"+
                    "\n"
            "athlete's foot" -> "Athlete's foot is a fungal infection causing itching and cracked skin on the feet. Keep feet clean and dry, use antifungal cream, and avoid walking barefoot in public places.\n"+
                    "\n"
            "ringworm" -> "Ringworm is a fungal infection forming red, ring-shaped rashes. Keep the affected area clean and dry, use antifungal cream, avoid scratching, and practice good hygiene.\n"+
                    "\n"
            "nail fungus" -> "Nail fungus causes discolored, thickened, and brittle nails. Maintain foot hygiene, use antifungal treatments as prescribed, and wear breathable shoes.\n"+
                    "\n"
            "cutaneous larva migrans" -> "Cutaneous larva migrans is caused by hookworm larvae burrowing into the skin. Avoid walking barefoot in at-risk areas, treat with prescription antiparasitic medication, keep the infected area clean, and avoid scratching.\n"+
                    "\n"
            "shingles" -> "Shingles causes painful rash and blisters on one side of the body. Treat with prescription antiviral medication, keep the rash clean and covered, and avoid contact with vulnerable individuals until the rash crusts over.\n"+
                    "\n"
            else -> "Tetap Pertahankan gaya hidup sehat anda"
        }
        binding.adviceText.text = advice
        val formattedDate = formatDateToIndonesia(data.createdAt)
        binding.createAt.text = "Created at: $formattedDate"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarResulArticle.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}