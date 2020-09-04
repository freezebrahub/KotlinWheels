package com.example.kotlindemo

import android.app.Application

class DemoApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()

        application = this
    }

}