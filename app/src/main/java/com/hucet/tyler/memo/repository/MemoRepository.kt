package com.hucet.tyler.memo.repository

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.fullTextSql
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
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

    fun searchMemos(keyword: String): LiveData<List<MemoView>> {
        return memoDao.searchMemo(keyword.fullTextSql())
    }

    fun insertMemo(memo: Memo): Long? {
        return memoDao.insert(memo).firstOrNull()
    }

    fun insertMemos(memos: List<Memo>): List<Long> {
        return memoDao.insert(memos)
    }

    fun insertCheckItems(checkItems: List<CheckItem>) {
        checkItemDao.insert(checkItems)
    }

    fun updateMemos(memos: List<Memo>) {
        memoDao.update(memos)
    }

    fun updateColorTheme(colorTheme: ColorTheme, memoId: Long) {
        val (label, color, textColor) = colorTheme
        return memoDao.updateColorTheme(label, color, textColor, memoId)
    }
}