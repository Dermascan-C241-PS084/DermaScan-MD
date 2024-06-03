package com.project.bangkit.dermascan.ui.auth.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.bangkit.dermascan.databinding.ActivitySignupBinding
import com.project.bangkit.dermascan.ui.auth.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.signupButton.setOnClickListener{
            validation()
        }

        viewModel.response.observe(this){
            AlertDialog.Builder(this).apply {
                setTitle("Done!")
                setMessage(it?.message)
                setPositiveButton("Lanjut") { _, _ ->
                    Intent(this@SignupActivity, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                }
                create()
                show()
            }
        }

        viewModel.isLoading.observe(this){isLoading ->
            if (isLoading){
                binding.progresbarSignup.visibility = View.VISIBLE
            }else{
                binding.progresbarSignup.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun validation(){
        binding.apply {
            val email = binding.emailEditTextLayout.editText?.text.toString()
            val password = binding.passwordEditTextLayout.editText?.text.toString()
            val name = binding.nameEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()){
                register(name, email, password)
            }  else {
                Toast.makeText(
                    this@SignupActivity,
                    "Please Filled All of Column",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        viewModel.apply {
            register(name, email, password)
        }
    }
}