package com.hucet.tyler.memo.ui.add

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_ID

data class EditMemoView(
        @Embedded
        var memo: Memo = Memo.empty(),
        @Relation(parentColumn = MEMO_ID, entityColumn = MEMO_ID, entity = CheckItem::class)
        var checkItems: List<CheckItem>? = null
)