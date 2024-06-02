package com.project.bangkit.dermascan.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.bangkit.dermascan.databinding.FragmentProfileBinding
import com.project.bangkit.dermascan.ui.aboutus.AboutUsActivity
import com.project.bangkit.dermascan.ui.editprofil.EditProfileActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "edit clicked", Toast.LENGTH_SHORT).show()
        }
        binding.btnAboutUs.setOnClickListener {
            startActivity(Intent(requireContext(), AboutUsActivity::class.java))
        }
        binding.btnEditProfile.setOnClickListener{
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}