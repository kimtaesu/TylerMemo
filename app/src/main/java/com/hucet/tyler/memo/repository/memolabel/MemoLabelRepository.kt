package com.hucet.tyler.memo.repository.memolabel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.persistence.db.SimpleSQLiteQuery
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.fullTextSql
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.ui.memo.MemoPreviewView
import com.hucet.tyler.memo.ui.label.CheckableLabelView
import com.hucet.tyler.memo.repository.label.LabelRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import io.reactivex.Observable
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class MemoLabelRepository @Inject constructor(
        private val dao: MemoLabelJoinDao,
        private val labelRepository: LabelRepository,
        private val memoRepository: MemoRepository
) : LabelRepository by labelRepository, MemoRepository by memoRepository {

    fun insertMemoLabelJoin(memoLabelJoin: MemoLabelJoin) {
        dao.insert(memoLabelJoin)
    }

    fun searchCheckedLabels(memoId: Long, keyword: String = ""): LiveData<List<CheckableLabelView>> {
        return dao.searchCheckedLabels(keyword.fullTextSql(), memoId)
    }

    fun getLabelByMemo(memoId: Long): LiveData<List<Label>> {
        return dao.getLabelByMemo(memoId)
    }

    fun getMemoByLabel(labelId: Long): LiveData<List<Memo>> {
        return dao.getMemoByLabel(labelId)
    }

    fun searchMemoView(keyword: String, isPinSort: Boolean = true): LiveData<List<MemoPreviewView>> {
        return Transformations.map(dao.searchMemoView(keyword.fullTextSql(), true)) {
            it.map {
                MemoPreviewView(it.memo, it.labels ?: emptyList())
            }
        }
    }

    fun deleteById(memoId: Long, labelId: Long)  = dao.deleteById(memoId, labelId)
}
