package com.gs.nasa.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gs.nasa.R
import com.gs.nasa.pod.view.PicturesActivity

class SplashActivity : AppCompatActivity() {

    internal companion object {
        private const val SPLASH_DELAY = 3000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        if (!isFinishing) {
            redirect()
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed(runnable, SPLASH_DELAY)
    }

    private fun redirect() {
        startActivity(Intent(this, PicturesActivity::class.java))
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        finish()
    }
}