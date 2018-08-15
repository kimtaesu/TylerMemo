package com.hucet.tyler.memo.ui.list

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.vo.Memo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class MemoRepository @Inject constructor(
        db: MemoDb
) {
    private val memoDao = db.memoDao()
    fun searchMemos(keyword: String): LiveData<List<Memo>> {
        return memoDao.search()
    }

    fun inserMemos(memos: List<Memo>) {
        memoDao.insert(memos)
    }
}