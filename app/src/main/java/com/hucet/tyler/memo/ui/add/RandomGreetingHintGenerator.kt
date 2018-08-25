package com.hucet.tyler.memo.ui.add

import com.hucet.tyler.memo.firebase.RemoteConfigProvider
import java.util.*

object RandomGreetingHintGenerator {
    val hints = RemoteConfigProvider.randomGreetingMemoHints
    fun generate(): String {
        val index = Random().nextInt(hints.size)
        return hints[index]
    }
}