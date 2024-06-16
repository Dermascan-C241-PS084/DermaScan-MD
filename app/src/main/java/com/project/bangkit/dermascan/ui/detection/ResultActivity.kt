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
            "chickenpox" -> """
                - Consult a healthcare provider for antiviral medication if necessary.
                - Avoid scratching the blisters to prevent scarring and secondary infection.
                - Use calamine lotion or oatmeal baths to soothe itching.
                - Stay hydrated and rest well to support recovery.
            """.trimIndent()
            "cellulitis" -> """
                - Seek medical attention promptly to receive antibiotics.
                - Keep the affected area clean and dry.
                - Elevate the affected limb to reduce swelling.
                - Avoid scratching or touching the infected area to prevent the spread.
            """.trimIndent()
            "impetigo" -> """
                - Maintain good hygiene by washing hands frequently.
                - Avoid close contact with others to prevent spreading the infection.
                - Keep the infected area covered with a clean bandage.
            """.trimIndent()
            "athlete's foot" -> """
                - Use over-the-counter antifungal creams or powders.
                - Keep feet clean and dry, especially between the toes.
                -  Wear breathable, well-ventilated shoes and moisture-wicking socks.
                - Avoid walking barefoot in public areas like locker rooms and pools.

            """.trimIndent()
            "ringworm" -> """
                - Use over-the-counter antifungal creams or ointments.
                - Keep the affected area clean and dry.
                - Avoid sharing personal items like towels or clothing.
                - Wash bedding and clothing frequently to prevent reinfection.

            """.trimIndent()
            "nail fungus" -> """
                - Apply topical antifungal treatments or take oral antifungal medication as prescribed.
                - Trim and clean nails regularly.
                - Avoid sharing nail clippers or footwear.
                - Keep nails dry and avoid prolonged exposure to moist environments.

            """.trimIndent()
            "cutaneous larva migrans" -> "Cutaneous larva migrans is caused by hookworm larvae burrowing into the skin. Avoid walking barefoot in at-risk areas, treat with prescription antiparasitic medication, keep the infected area clean, and avoid scratching.\n"+
                    "\n"
            "shingles" -> """
               - Consult a healthcare provider for appropriate antiparasitic treatment.
               - Avoid walking barefoot on sandy or moist soil where the parasite is common.
               - Keep the affected area clean and avoid scratching.
               - Use insect repellent and wear protective clothing in areas where the parasite is prevalent.
            """.trimIndent()
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