package com.project.bangkit.dermascan.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.databinding.ActivityMainBinding
import com.project.bangkit.dermascan.ui.fragment.Article
import com.project.bangkit.dermascan.ui.fragment.History
import com.project.bangkit.dermascan.ui.fragment.HomeFragment
import com.project.bangkit.dermascan.ui.fragment.Profile
import com.project.bangkit.dermascan.ui.scan.ScanActivity

class MainActivity: AppCompatActivity() {

    private lateinit var navview : BottomNavigationView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replace(HomeFragment())

        binding.btmBarScan.setOnClickListener {
            Intent(this, ScanActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replace(HomeFragment())
                R.id.navigation_article -> replace(Article())
                R.id.navigation_history -> replace(History())
                R.id.navigation_profile -> replace(Profile())
            }
            true
        }

    }

    private fun replace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navhost, fragment)
        fragmentTransaction.commit()
    }

}
