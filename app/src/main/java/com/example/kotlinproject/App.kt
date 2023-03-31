package com.example.kotlinproject

import android.app.Application

class App : Application() {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { App() }
    }
}