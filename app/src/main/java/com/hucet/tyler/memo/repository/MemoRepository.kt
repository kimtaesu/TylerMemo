package com.hucet.tyler.memo.repository

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.CheckItem
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import timber.log.Timber
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
        if (keyword.isEmpty())
            return memoDao.allMemo()
        return memoDao.searchMemo("%$keyword%")
    }

    fun insertMemo(memo: Memo): Long? {
        return memoDao.insert(memo).firstOrNull()
    }

    fun insertMemos(memos: List<Memo>) {
        memoDao.insert(memos)
    }

    fun searchLabels(keyword: String): LiveData<List<Label>> {
        Timber.d("thread: ${Thread.currentThread().name}")
        if (keyword.isEmpty())
            return labelDao.all()
        return labelDao.searchLabels("%$keyword%")
    }

    fun insertLabel(label: Label) {
        Timber.d("insertLabel ${label}")
        labelDao.insert(label)
    }

    fun insertCheckItems(checkItems: List<CheckItem>) {
        checkItemDao.insert(checkItems)
    }

    fun updateMemos(memos: List<Memo>) {
        memoDao.update(memos)
    }

    fun findMemoById(id: Long): LiveData<MemoView> {
        return memoDao.findMemo(id)
    }

    fun updateColorTheme(colorTheme: ColorTheme, memoId: Long) {
        val (label, color, textColor) = colorTheme
        return memoDao.updateColorTheme(label, color, textColor, memoId)
    }
}