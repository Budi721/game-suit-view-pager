package com.example.challengechapter5

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        window.insetsController?.hide(WindowInsets.Type.statusBars())
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ScreenSlideActivity::class.java))
            finish()
        }, 3000)
    }
}