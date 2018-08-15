package com.hucet.tyler.memo.list

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.db.MemoDao
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.vo.Memo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoRepository @Inject constructor(
        private val dao: MemoDao
) {
    fun allMemos(): LiveData<List<Memo>> {
        return dao.all()
    }

    fun inserMemos(memos: List<Memo>) {
        dao.insert(memos)
    }
}