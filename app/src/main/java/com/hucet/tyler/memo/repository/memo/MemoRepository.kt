package com.hucet.tyler.memo.repository.memo

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import javax.inject.Inject
import javax.inject.Singleton


interface MemoRepository {
    fun insertMemo(memo: Memo): Long?
    fun insertMemos(memos: List<Memo>): List<Long>
    fun updateColorTheme(colorTheme: ColorTheme, memoId: Long)
    fun searchMemos(keyword: String): LiveData<Memo>
    @Singleton
    @OpenForTesting
    class MemoRepositoryImpl @Inject constructor(private val dao: MemoDao) : MemoRepository {
        override fun insertMemo(memo: Memo): Long? = dao.insert(memo).firstOrNull()
        override fun insertMemos(memos: List<Memo>): List<Long> = dao.insert(memos)
        override fun updateColorTheme(colorTheme: ColorTheme, memoId: Long) {
            val (label, color, textColor) = colorTheme
            return dao.updateColorTheme(label, color, textColor, memoId)
        }
    }
}
