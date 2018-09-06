package com.hucet.tyler.memo.dto

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_ID

class MemoView {
    @Embedded
    var memo: Memo = Memo.empty()

    @Relation(parentColumn = MEMO_ID, entityColumn = MEMO_ID, entity = Label::class)
    var labels: List<Label>? = null

    @Relation(parentColumn = MEMO_ID, entityColumn = MEMO_ID, entity = CheckItem::class)
    var checkItems: List<CheckItem>? = null
}