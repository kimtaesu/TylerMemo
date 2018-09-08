package com.hucet.tyler.memo.dto

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_ID
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.db.model.MemoLabelJoin.Companion.MEMO_LABEL_JOIN_ID

data class MemoView(
        @Embedded
        var memo: Memo = Memo.empty(),

        @Relation(parentColumn = Memo.MEMO_ID, entityColumn = MemoLabelJoin.MEMO_LABEL_JOIN_ID, entity = MemoLabelJoin::class, projection = [MemoLabelJoin.FOREIGN_LABEL_ID])
        var labels: List<Long>? = null,

        @Relation(parentColumn = MEMO_ID, entityColumn = MEMO_ID, entity = CheckItem::class)
        var checkItems: List<CheckItem>? = null
)
