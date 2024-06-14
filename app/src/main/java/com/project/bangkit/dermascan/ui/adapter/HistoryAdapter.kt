package com.project.bangkit.dermascan.ui.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.data.pref.DetectionResult
import com.project.bangkit.dermascan.databinding.ItemHistoryBinding
import com.project.bangkit.dermascan.ui.detail_history.DetailHistoryActivity

class HistoryAdapter(private val items: List<DetectionResult>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            // Bind data to views
            if (!item.imageUrl.isNullOrEmpty()) {
                Glide.with(ivHistoryImage.context)
                    .load(item.imageUrl)
                    .into(ivHistoryImage)
            } else {
                Log.d("ImageUrlCheck", "ImageUrl is null or empty")
            }
            tvHistoryResult.text = item.result
            tvHistoryDate.text = item.createdAt
            tvHistoryAdvice.text = item.advice

            // Add click listener to the item view
            root.setOnClickListener {
                val intent = Intent(it.context, DetailHistoryActivity::class.java)
                intent.putExtra("DETECTION_RESULT", item)
                it.context.startActivity(intent)
                if (it.context is Activity) {
                    (it.context as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }
        }
    }


    override fun getItemCount() = items.size
}