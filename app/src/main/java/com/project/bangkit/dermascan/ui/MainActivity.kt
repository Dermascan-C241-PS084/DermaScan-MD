package com.project.bangkit.dermascan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.ui.fragment.ArticleFragment
import com.project.bangkit.dermascan.ui.fragment.HistoryFragment
import com.project.bangkit.dermascan.ui.fragment.HomeFragment
import com.project.bangkit.dermascan.ui.fragment.ProfileFragment

class MainActivity: AppCompatActivity() {

    private lateinit var navview : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navview=findViewById(R.id.nav_view)

        replace(HomeFragment())

        navview.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replace(HomeFragment())
                R.id.navigation_article -> replace(ArticleFragment())
                R.id.navigation_history -> replace(HistoryFragment())
                R.id.navigation_profile -> replace(ProfileFragment())

            }
            true
        }

    }

    private fun replace(fragment: Fragment){
        val fragmenManager=supportFragmentManager
        val fragmentTransaction = fragmenManager.beginTransaction()
        fragmentTransaction.replace(R.id.navhost, fragment)
        fragmentTransaction.commit()

    }

}
