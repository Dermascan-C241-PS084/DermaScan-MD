package com.project.bangkit.dermascan.ui.opening

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.project.bangkit.dermascan.R

class SplashScreenActivityclass : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val splashDo = object : Thread(){
            override  fun run(){
                try {
                    Thread.sleep(2500)

                    val doIntent = Intent(baseContext, OpeningAppsActivity::class.java)
                    startActivity(doIntent)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        splashDo.start()
    }

}