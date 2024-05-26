package com.project.bangkit.dermascan.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.databinding.ActivityLoginBinding
import com.project.bangkit.dermascan.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {
//    private val viewModel by viewModels<LoginViewModel> {
//        ViewModelFactory.getInstance(this)
//    }
    private lateinit var binding: ActivityLoginBinding
//    private var dialogLoading: Dialog? = null
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        setupView()
        playAnimation()
        setUpAction()
//
//        binding.loginButton.setOnClickListener {
//            validation()
        }
//
//        viewModel.value.observe(this) {
//            viewModel.saveSession(UserModel(it.loginResult.userId, it.loginResult.name, it.loginResult.token))
//            AlertDialog.Builder(this).apply {
//                setTitle("Selamat!")
//                setMessage("Anda berhasil login!")
//                setPositiveButton("Lanjut") { _, _ ->
//                    val intent = Intent(context, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
//                }
//                create()
//                show()
//            }
//
//        }
//
//        viewModel.loginStatus.observe(this) { isSuccess ->
//            if (!isSuccess) {
//                Toast.makeText(this, "Gagal login coba periksa Email atau Password anda", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//        viewModel.isLoading.observe(this) {
//            showLoading(it)
//        }
//
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            if (dialogLoading == null) {
//                dialogLoading = Dialog(this)
//                dialogLoading!!.setContentView(R.layout.loading_dialog)
//                dialogLoading!!.window!!.setLayout(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//                )
//                dialogLoading!!.show()
//            }
//        } else {
//            dialogLoading?.dismiss()
//            dialogLoading = null
//        }
//
//    }

    private fun setUpAction(){
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
//
//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }
//
//    private fun validation() {
//        val email = binding.emailEditTextLayout.editText?.text.toString()
//        val password = binding.passwordEditTextLayout.editText?.text.toString()
//
//        when {
//            email.isEmpty() && password.isNotEmpty() -> Toast.makeText(
//                this,
//                "Please Email must be filled",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            email.isNotEmpty() && password.isEmpty() -> Toast.makeText(
//                this,
//                "Please Password must be filled",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            else -> login(email, password)
//        }
//    }
//
//    private fun login(email: String, password: String) {
//        viewModel.apply {
//            login(email, password)
//        }
//    }
//
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

}