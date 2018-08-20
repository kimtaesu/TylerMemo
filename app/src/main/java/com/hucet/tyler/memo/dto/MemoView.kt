package com.hucet.tyler.memo.dto

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import com.hucet.tyler.memo.vo.Memo.Companion.MEMO_ID

class MemoView {
    @Embedded
    var memo: Memo = Memo("", "")

    @Relation(parentColumn = MEMO_ID, entityColumn = MEMO_ID, entity = Label::class)
    var labels: List<Label>? = null
}