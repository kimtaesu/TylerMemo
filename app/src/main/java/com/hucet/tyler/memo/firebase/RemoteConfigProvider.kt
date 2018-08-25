package com.hucet.tyler.memo.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import timber.log.Timber

object RemoteConfigProvider {
    enum class Keys {
        RandomGreetingMemoHints
    }

    val randomGreetingMemoHints: List<String> by lazy {
        getString(Keys.RandomGreetingMemoHints.name).split(";")
    }

    private fun getString(key: String): String {
        val content = FirebaseRemoteConfig.getInstance().getString(key)
        Timber.e("$key $content")
        return content
    }
}