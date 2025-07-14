package com.example.karnaughmapsapplication

import android.app.Application
import com.example.karnaughmapsapplication.core.domain.di.DaggerKarnaughMapComponent
import com.example.karnaughmapsapplication.core.domain.di.KarnaughMapComponent

class KarnaughMapsApplication: Application() {
    lateinit var karnaughMapComponent: KarnaughMapComponent

    override fun onCreate() {
        super.onCreate()
        karnaughMapComponent = DaggerKarnaughMapComponent.create()
    }
}