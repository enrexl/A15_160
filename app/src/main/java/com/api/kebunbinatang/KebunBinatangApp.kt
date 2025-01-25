package com.api.kebunbinatang

import android.app.Application

class KebunBinatangApp: Application {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = KebunBinatangContainer
    }
}