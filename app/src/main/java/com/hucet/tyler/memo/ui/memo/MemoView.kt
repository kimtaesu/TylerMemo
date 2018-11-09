package com.hucet.tyler.memo.ui.memo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_ID

data class MemoView(val memo: Memo,
                    val labels: List<Label>
)