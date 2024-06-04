package com.project.bangkit.dermascan.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.bangkit.dermascan.databinding.FragmentHomeBinding
import com.project.bangkit.dermascan.ui.detection.ScanActivity
import com.project.bangkit.dermascan.ui.profil.ProfileViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvNameAccount.text = "Hi, ${user.name}"

        }

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
        binding.btLogout.setOnClickListener {
            // Handle the button click event
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Logout")
                setMessage("Are you sure you want to logout?")
                setPositiveButton("Logout") { _, _ ->
                    viewModel.logout()
                }
                setNegativeButton("Cancel", null)
                create()
                show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
