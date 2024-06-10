package com.project.bangkit.dermascan.ui.changepassword

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.databinding.ActivityChangePasswordBinding
import com.project.bangkit.dermascan.request.RequestEditProfile
import com.project.bangkit.dermascan.ui.profil.ProfileViewModel

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.user.observe(this) { user ->
            // Do not display the user's id in the currentPasswordEditTextLayout
        }
        viewModel.isLoading.observe(this) { isLoading ->
            // Show or hide the loading indicator based on the isLoading value
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.btnChangePassword.setOnClickListener {
            val newPassword = binding.passwordEditTextLayout.editText?.text.toString()
            val currentPassword = viewModel.user.value?.password

            if (newPassword == currentPassword) {
                // Display error message
                Toast.makeText(this, "New password cannot be the same as the current password", Toast.LENGTH_SHORT).show()
            } else {
                // Show loading
                binding.progressBar.visibility = View.VISIBLE

                // Delay for 2 seconds (2000 milliseconds)
                Handler(Looper.getMainLooper()).postDelayed({
                    // Update the password
                    val requestEditProfile = RequestEditProfile(currentPassword ?: "", newPassword)
                    viewModel.changePassword(viewModel.user.value?.userId ?: "", requestEditProfile)
                    // Add log
                    Log.d("ChangePasswordActivity", "Password updated successfully")
                    showCustomDialogPass()

                    // Hide loading
                    binding.progressBar.visibility = View.GONE
                }, 2000)
            }
        }


        binding.btnBackChangePassword.setOnClickListener{
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_PASSWORD", binding.currentPasswordEditTextLayout.editText?.text.toString())
        outState.putString("NEW_PASSWORD", binding.passwordEditTextLayout.editText?.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.currentPasswordEditTextLayout.editText?.setText(savedInstanceState.getString("CURRENT_PASSWORD"))
        binding.passwordEditTextLayout.editText?.setText(savedInstanceState.getString("NEW_PASSWORD"))
    }
    private fun showCustomDialogPass() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_change_pass, null)
        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .show()

        val closeButton: Button = dialogView.findViewById(R.id.button_close_pass) // Remove the default button background
        closeButton.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

}