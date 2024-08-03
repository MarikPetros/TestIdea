package com.example.testidea

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TestIdeaApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@TestIdeaApplication)
            modules(ideaAppModule)
        }
    }
}