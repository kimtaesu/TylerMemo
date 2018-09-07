package com.hucet.tyler.memo.binding

import android.databinding.BindingAdapter
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.TextView
import com.hucet.tyler.memo.common.SpanTokenizer
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.ui.view.RoundedBackgroundSpan
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import timber.log.Timber


@BindingAdapter("bind:spanLabels")
fun spanLabels(view: TextView, labels: List<Label>) {
    val joinString = labels.joinToString(separator = ",") { it.label }
    view.text = SpannableStringBuilder(" ").apply {
        runBlocking {
            for (span in SpanTokenizer.generateToken(joinString)) {
                Timber.d("span ${span}")
                append(span.s, RoundedBackgroundSpan(view.context, Color.RED, Color.WHITE, 100f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
    println("!!!!!!!! span")
}