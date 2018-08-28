package com.hucet.tyler.memo.common

import android.content.Intent
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.UNKNOWN_ID
import com.hucet.tyler.memo.vo.Memo

fun Intent.toMemo(): Memo {
    val memoId = getLongExtra(ArgKeys.KEY_MEMO_ID.name, UNKNOWN_ID) ?: UNKNOWN_ID
    return (getParcelableExtra(ArgKeys.KEY_MEMO.name) as Memo).apply {
        id = memoId
    }
}