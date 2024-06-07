package com.project.bangkit.dermascan.ui.editprofil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.databinding.ActivityEditProfileBinding
import com.project.bangkit.dermascan.request.RequestEditProfile
import com.project.bangkit.dermascan.ui.profil.ProfileViewModel

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        progressBar = findViewById(R.id.progressBar)

        // Observe user data and set it to the UI components
        viewModel.user.observe(this) { user ->
            binding.cpName.setText(user.name)
            binding.cpEmail.setText(user.email)
        }

        // Handle profile update button click
        binding.btnChangeProfile.setOnClickListener {
            val newName = binding.cpName.text.toString()
            val newEmail = binding.cpEmail.text.toString()
            val currentUser = viewModel.user.value

            if (currentUser != null) {
                if (newName == currentUser.name && newEmail == currentUser.email) {
                    Toast.makeText(this, "Please make changes to update profile", Toast.LENGTH_SHORT).show()
                } else {
                    val userId = currentUser.userId
                    val requestEditProfile = RequestEditProfile(newName, newEmail)
                    viewModel.editProfile(userId, requestEditProfile)
                }
            }

            // Observe isLoading LiveData to show/hide ProgressBar
            viewModel.isLoading.observe(this) { isLoading ->
                if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }

        // Observe editResponse LiveData to handle the response from the editProfile API call
        viewModel.editResponse.observe(this) { response ->
            if (response != null) {
               showCustomDialog()
            } else {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe statusMessage LiveData to show toast messages
        viewModel.statusMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Observe loading LiveData to show/hide ProgressBar
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        // Handle back button click
        binding.btnBackEditProfile.setOnClickListener {
            finish()
        }
    }

    private fun showCustomDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .show()

        val closeButton: Button = dialogView.findViewById(R.id.button_close) // Remove the default button background
        closeButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }
}