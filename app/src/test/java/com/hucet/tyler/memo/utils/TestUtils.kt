package com.hucet.tyler.memo.utils

import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.LabelRepository
import com.hucet.tyler.memo.repository.MemoRepository

object TestUtils {
    fun generateMemoLabel(db: MemoDb, count: Int = 5) {
        MemoRepository(db).apply {
            val memos = listOf(0 until count)
                    .flatten()
                    .mapIndexed { index, i ->
                        Memo("memo_${i + 1}")
                    }
            insertMemos(memos)
        }
        LabelRepository(db).apply {
            val labels = listOf(0 until count)
                    .flatten()
                    .mapIndexed { index, i ->
                        Label("label_${i + 1}")
                    }
            insertLabels(labels)
        }
    }
}