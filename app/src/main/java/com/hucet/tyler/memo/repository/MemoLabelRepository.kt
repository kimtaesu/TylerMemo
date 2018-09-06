package com.hucet.tyler.memo.repository

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.fullTextSql
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.dto.MemoView2
import com.hucet.tyler.memo.vo.CheckableLabelView
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class MemoLabelRepository @Inject constructor(
        db: MemoDb
) {
    private val memoLabelJoinDao by lazy { db.memoLabelJoinDao() }

    fun insertMemoLabelJoin(memoLabelJoin: MemoLabelJoin) {
        memoLabelJoinDao.insert(memoLabelJoin)
    }

    fun searchCheckedLabels(memoId: Long, keyword: String = ""): LiveData<List<CheckableLabelView>> {
        return memoLabelJoinDao.searchCheckedLabels(keyword.fullTextSql(), memoId)
    }

    fun getLabelByMemo(memoId: Long): LiveData<List<Label>> {
        return memoLabelJoinDao.getLabelByMemo(memoId)
    }

    fun getMemoByLabel(labelId: Long): LiveData<List<Memo>> {
        return memoLabelJoinDao.getMemoByLabel(labelId)
    }

    fun deleteById(memoId: Long, labelId: Long) {
        memoLabelJoinDao.deleteById(memoId, labelId)
    }

    fun searchMemoLabels(keyword: String): LiveData<List<MemoView2>> {
        return memoLabelJoinDao.searchMemoLabels(keyword.fullTextSql())
    }
}
