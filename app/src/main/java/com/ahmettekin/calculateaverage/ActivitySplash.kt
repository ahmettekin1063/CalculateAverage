package com.ahmettekin.calculateaverage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*


class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val asagidanGelenButton = AnimationUtils.loadAnimation(this, R.anim.asagidan_gelen_button)
        val yukaridanGelenBalon = AnimationUtils.loadAnimation(this, R.anim.yukaridan_gelen_balon)
        val asagiyaGeriDonenButton = AnimationUtils.loadAnimation(this, R.anim.asagi_giden_button)
        val yukariGidenBalon = AnimationUtils.loadAnimation(this, R.anim.yukari_giden_balon)

        startButton.startAnimation(asagidanGelenButton)
        imageView.startAnimation(yukaridanGelenBalon)


        startButton.setOnClickListener {

            startButton.startAnimation(asagiyaGeriDonenButton)
            imageView.startAnimation(yukariGidenBalon)

            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
            }.start()
            /*
            Handler().postDelayed({
                startActivity(Intent(this@ActivitySplash, MainActivity::class.java))
                finish()
            }, 1000)
            */
        }
    }
}