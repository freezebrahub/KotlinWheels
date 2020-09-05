package com.example.kotlindemo

import android.net.Uri
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.utilbox.ScreenAdapter

class MainActivity : AppCompatActivity() {
    private val _tag: String = "MainActivity"

    private lateinit var videoView: VideoView
    private lateinit var versionTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        videoView = findViewById(R.id.activity_main_vv)
        versionTv = findViewById(R.id.activity_main_version_tv)

        setupAndPlayVideo()
        ScreenAdapter.getInstance(this).adaptionView(versionTv)

        executeVersionTvAnimation()
    }

    private fun executeVersionTvAnimation() {
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 1500
        animation.interpolator = AccelerateInterpolator()
        versionTv.startAnimation(animation)
    }

    private fun setupAndPlayVideo() {
        val playPath = "android.resource://$packageName/raw/splash_video2"
        videoView.setVideoURI(Uri.parse(playPath))
        videoView.start()
    }
}