package com.hucet.tyler.memo.ui.list

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.CheckItem
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class MemoRepository @Inject constructor(
        db: MemoDb
) {
    private val memoDao by lazy { db.memoDao() }
    private val labelDao by lazy { db.labelDao() }
    private val checkItemDao by lazy { db.checkItemDao() }

    fun searchMemos(keyword: String): LiveData<List<Memo>> {
        return memoDao.search()
    }

    fun searchMemoViews(keyword: String): LiveData<List<MemoView>> {
        return memoDao.searchMemoView()
    }

    fun insertMemos(memos: List<Memo>) {
        memoDao.insert(memos)
    }

    fun insertLabels(label: List<Label>) {
        labelDao.insert(label)
    }

    fun insertCheckItems(checkItems: List<CheckItem>) {
        checkItemDao.insert(checkItems)
    }
}