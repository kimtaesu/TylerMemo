package com.hucet.tyler.memo.utils

import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.checkitem.CheckItemRepository
import com.hucet.tyler.memo.repository.label.LabelRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository

object TestUtils {
    fun createMemoLabelRepository(db: MemoDb): MemoLabelRepository {
        return MemoLabelRepository(
                db.memoLabelJoinDao(),
                LabelRepository.LabelRepositoryImpl(db.labelDao()),
                MemoRepository.MemoRepositoryImpl(db.memoDao(), CheckItemRepository.CheckItemRepositoryImpl(db.checkItemDao()))
        )
    }

    fun generateMemoLabel(db: MemoDb, count: Int = 5): Pair<List<Memo>, List<Label>> {
        val memos = listOf(0 until count)
                .flatten()
                .mapIndexed { index, i ->
                    Memo("memo_${i + 1}", id = (i + 1).toLong())
                }

        val labels = listOf(0 until count)
                .flatten()
                .mapIndexed { index, i ->
                    Label("label_${i + 1}", id = (i + 1).toLong())
                }

        MemoRepository.MemoRepositoryImpl(db.memoDao(), CheckItemRepository.CheckItemRepositoryImpl(db.checkItemDao())).apply {
            insertMemos(memos)
        }
        LabelRepository.LabelRepositoryImpl(db.labelDao()).apply {
            insertLabels(labels)
        }
        return memos to labels
    }
}