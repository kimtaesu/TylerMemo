package com.hucet.tyler.memo.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.fullTextSql
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.vo.CheckableLabelView
import com.hucet.tyler.memo.db.model.Label
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
        return labelDao.searchLabels(keyword.fullTextSql())
    }

    fun insertLabel(label: Label): Long? {
        Timber.d("insert label: ${label}")
        return labelDao.insert(label).lastOrNull()
    }

    fun insertLabels(labels: List<Label>): List<Long> {
        return labelDao.insert(labels)
    }
}