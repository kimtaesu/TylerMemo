package com.hucet.tyler.memo

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hucet.tyler.memo.debug.OptionalTree
import com.hucet.tyler.memo.di.AppInjector
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

open class MyApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        FirebaseApp.initializeApp(this)
        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettings(FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build())
            setDefaults(R.xml.default_remote_config)
        }.run {
            val cacheExpirationSecond = if (BuildConfig.DEBUG) 0L else 60 * 60 * 12 // 12 hours
            fetch(cacheExpirationSecond).addOnCompleteListener { task ->
                if (task.isSuccessful) activateFetched()
            }
        }
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
