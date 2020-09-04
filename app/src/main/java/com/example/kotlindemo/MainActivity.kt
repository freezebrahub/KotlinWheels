package com.example.kotlindemo

import android.net.Uri
import android.os.Bundle
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
    }

    private fun setupAndPlayVideo() {
        val playPath = "android.resource://$packageName/raw/splash_video2"
        videoView.setVideoURI(Uri.parse(playPath))
        videoView.start()
    }
}