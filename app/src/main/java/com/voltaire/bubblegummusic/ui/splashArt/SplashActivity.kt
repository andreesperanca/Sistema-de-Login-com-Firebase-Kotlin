package com.voltaire.bubblegummusic.ui.splashArt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.voltaire.bubblegummusic.R
import com.voltaire.bubblegummusic.ui.login.view.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_art)

        val imgSplash = findViewById<ImageView>(R.id.splash_art)

        imgSplash.animate().apply {
            duration = 2000
            alpha(1.0f)
            start()

            Handler(Looper.getMainLooper()).postDelayed({
                imgSplash.animate().apply {
                    duration = 2000
                    alpha(0.0f)
                    start()
                }
                val intent = Intent(baseContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            },4000 )
        }
    }
}
