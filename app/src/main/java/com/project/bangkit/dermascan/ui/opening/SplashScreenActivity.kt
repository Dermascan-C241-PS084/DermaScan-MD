package com.project.bangkit.dermascan.ui.opening

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.ui.main.MainActivity
import com.project.bangkit.dermascan.ui.profil.ProfileViewModel

class SplashScreenActivityclass : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.user.observe(this) { user ->
                val intent = if (user.isLogin) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, OpeningAppsActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }, 750)
    }
}