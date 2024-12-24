package com.example.ucproomdatabase

import android.app.Application
import com.example.ucproomdatabase.dependenciesinjection.ContainerApp

class DawgApp : Application() {
    lateinit var containerApp: ContainerApp
    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}