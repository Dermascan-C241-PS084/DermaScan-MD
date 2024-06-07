package com.project.bangkit.dermascan.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.databinding.ActivityMainBinding
import com.project.bangkit.dermascan.ui.ViewModelFactory
import com.project.bangkit.dermascan.ui.article.ArticleFragment
import com.project.bangkit.dermascan.ui.detection.ScanActivity
import com.project.bangkit.dermascan.ui.history.HistoryFragment
import com.project.bangkit.dermascan.ui.home.HomeFragment
import com.project.bangkit.dermascan.ui.opening.SplashScreenActivityclass
import com.project.bangkit.dermascan.ui.profil.ProfileFragment

class MainActivity: AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private var dialogLoading: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, SplashScreenActivityclass::class.java))
            }
        }

        if (savedInstanceState == null) {
            replace(HomeFragment())
        } else {
            binding.navView.selectedItemId = savedInstanceState.getInt("selectedItemId")
        }

        setupView()

        viewModel.isLoading.observe(this) { showLoading(it) }

        binding.btmBarScan.setOnClickListener {
            Intent(this, ScanActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replace(HomeFragment())
                R.id.navigation_article -> replace(ArticleFragment())
                R.id.navigation_history -> replace(HistoryFragment())
                R.id.navigation_profile -> replace(ProfileFragment())
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", binding.navView.selectedItemId)
    }

    private fun replace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navhost, fragment)
        fragmentTransaction.commit()
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
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            if (dialogLoading == null) {
                dialogLoading = Dialog(this)
                dialogLoading!!.setContentView(R.layout.loading_dialog)
                dialogLoading!!.window!!.setLayout(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                dialogLoading!!.show()
            }
        } else {
            dialogLoading?.dismiss()
            dialogLoading = null
        }
    }
}