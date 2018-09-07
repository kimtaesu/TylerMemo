package com.hucet.tyler.memo.common

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce

object SpanTokenizer {
    data class SpanToken(val s: CharSequence, val start: Int, val end: Int)

    fun generateToken(str: String) = produce<SpanToken> {
        var startIndex = 0
        var count = 0
        while (true) {
            val endIndex = str.indexOf(",", startIndex)
            if (endIndex == -1) break
            send(SpanToken(str.subSequence(startIndex, endIndex), startIndex, endIndex))
            startIndex = endIndex + 1
            count += 1
        }
    }
}