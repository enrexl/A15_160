package com.api.kebunbinatang

import android.app.Application
import com.api.kebunbinatang.di.AppContainer
import com.api.kebunbinatang.di.KebunBinatangContainer

class KebunBinatangApp: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = KebunBinatangContainer()
    }
}