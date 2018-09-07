package com.hucet.tyler.memo.common

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.channels.consumeEachIndexed
import kotlinx.coroutines.experimental.runBlocking
import org.amshove.kluent.`should equal`
import org.junit.Test

class SpanTokenizerTest {

    @Test
    fun `iterateToken`() = runBlocking {
        val expects = listOf(
                SpanTokenizer.SpanToken("", 0, 0),
                SpanTokenizer.SpanToken("asd", 1, 4),
                SpanTokenizer.SpanToken("bbb", 5, 8),
                SpanTokenizer.SpanToken("ccc", 9, 12)
        )

        val receiveChannel = SpanTokenizer.generateToken(",asd,bbb,ccc,a")
        receiveChannel.consumeEachIndexed {
            it.value `should equal` expects[it.index]
        }
    }

}