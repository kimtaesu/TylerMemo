package com.hucet.tyler.memo.repository

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.CheckItem
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class LabelRepository @Inject constructor(
        db: MemoDb
) {
    private val labelDao by lazy { db.labelDao() }

    fun searchLabels(keyword: String): LiveData<List<Label>> {
        Timber.d("thread: ${Thread.currentThread().name}")
        if (keyword.isEmpty())
            return labelDao.all()
        return labelDao.searchLabels("%$keyword%")
    }

    fun insertLabel(memo: Label) {
        Timber.d("${memo}")
        labelDao.insert(memo)
    }
}