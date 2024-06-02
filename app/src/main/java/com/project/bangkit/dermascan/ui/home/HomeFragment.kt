package com.project.bangkit.dermascan.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.bangkit.dermascan.databinding.FragmentHomeBinding
import com.project.bangkit.dermascan.ui.detection.ScanActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set onClickListener for the logout button
//        binding.tvContainArticle.setOnClickListener {
//            // Handle the button click event
//           Intent(requireContext(), Article::class.java).also {
//                startActivity(it)
//            }
//            // You can add your logout logic here
//        }

        // Set onClickListener for the scan button
        binding.iButtonScan.setOnClickListener {
            // Handle the button click event
            startActivity(Intent(requireContext(), ScanActivity::class.java))
        }

        // Set text for tv_name_account
//        binding.tvNameAccount.text = getString(R.string.welcome_user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
