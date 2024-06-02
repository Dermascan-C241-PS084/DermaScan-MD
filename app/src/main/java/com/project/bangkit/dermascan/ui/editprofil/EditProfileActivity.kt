package com.project.bangkit.dermascan.ui.editprofil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.btnBackEditProfile.setOnClickListener {
            finish()
        }
    }
}