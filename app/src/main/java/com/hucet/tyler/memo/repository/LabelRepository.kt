package com.hucet.tyler.memo.repository

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.vo.CheckableLabelView
import com.hucet.tyler.memo.vo.Label
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
        if (keyword.isEmpty())
            return labelDao.all()
        return labelDao.searchLabels("%$keyword%")
    }

    fun searchCheckedLabels(keyword: String): LiveData<List<CheckableLabelView>> {
        return labelDao.searchCheckedLabels("%$keyword%")
    }


    fun insertLabel(label: Label) {
        Timber.d("insert label: ${label}")
        labelDao.insert(label)
    }
}