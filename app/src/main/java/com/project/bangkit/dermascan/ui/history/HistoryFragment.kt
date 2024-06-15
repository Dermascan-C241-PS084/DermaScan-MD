package com.project.bangkit.dermascan.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.bangkit.dermascan.data.pref.DetectionResult
import com.project.bangkit.dermascan.databinding.FragmentHistoryBinding
import com.project.bangkit.dermascan.ui.adapter.HistoryAdapter

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create an adapter with the list of results
        val adapter = HistoryAdapter(detectionResults.reversed())

        // Set the adapter for the RecyclerView
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.setHasFixedSize(true)

        // Access title_history_pasif view
        binding.titleHistoryPasif.visibility = View.VISIBLE
        binding.titleHistoryPasif.text = "History Pasif"// Show loading
        binding.progressBarHistory.visibility = View.VISIBLE

        // Load data
        // After data is loaded, hide loading
        binding.progressBarHistory.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        val detectionResults = mutableListOf<DetectionResult>()
    }
}