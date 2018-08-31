package com.hucet.tyler.memo

import com.facebook.stetho.Stetho
import com.hucet.tyler.memo.debug.OptionalTree
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class DebugApplication : MyApplication() {
    override fun onCreate() {
        super.onCreate()
        initLeakCanary()
        initStetho()
        initTimber()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }

    private fun initTimber() {
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
        if (BuildConfig.DEBUG) {
            Timber.plant(OptionalTree(threadName = true))
        }
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);
    }
}