package com.project.bangkit.dermascan.ui.profil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.bangkit.dermascan.databinding.FragmentProfileBinding
import com.project.bangkit.dermascan.ui.aboutus.AboutUsActivity
import com.project.bangkit.dermascan.ui.changepassword.ChangePasswordActivity
import com.project.bangkit.dermascan.ui.editprofil.EditProfileActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.profileName.text = user.name
//            token adlaah email
            binding.profileEmail.text = user.email
            binding.profileImage.text = user.name.firstOrNull()?.toUpperCase()?.toString() ?: ""
            Log.d("ProfileFragment", "User name: ${user.name}, User email: ${user.email}")

        }

        binding.btnLogoutProfile.setOnClickListener {
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        viewModel.editResponse.observe(viewLifecycleOwner, { response ->
            Log.d("ProfileFragment", "Edit profile response: $response")
            if (response != null) {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        })
        binding.btnAboutUs.setOnClickListener {
            startActivity(Intent(requireContext(), AboutUsActivity::class.java))
        }
        binding.btnEditProfile.setOnClickListener{
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        binding.btnChangePassword.setOnClickListener{
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}