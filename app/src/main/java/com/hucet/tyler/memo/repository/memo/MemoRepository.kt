package com.hucet.tyler.memo.repository.memo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoAttribute
import com.hucet.tyler.memo.repository.checkitem.CheckItemRepository
import com.hucet.tyler.memo.ui.add.EditMemoView
import com.hucet.tyler.memo.utils.toObservable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


interface MemoRepository : CheckItemRepository {
    fun insertMemo(memo: Memo): Long?
    fun insertMemos(memos: List<Memo>): List<Long>
    fun updateColorTheme(memoId: Long, colorTheme: ColorTheme)
    fun getEditMemoById(memoid: Long): LiveData<EditMemoView>
    fun updateMemoText(memoid: Long, text: String)
    fun updateMemoAttr(memoid: Long, attribute: MemoAttribute)
    fun updateMemo(memo: Memo)

    @Singleton
    @OpenForTesting
    class MemoRepositoryImpl @Inject constructor(private val dao: MemoDao, checkItemRepository: CheckItemRepository)
        : MemoRepository, CheckItemRepository by checkItemRepository {

        override fun updateMemoAttr(memoid: Long, attribute: MemoAttribute) = dao.updateMemoAttr(memoid, attribute.isPin)
        override fun updateMemo(memo: Memo) =
                dao.updateMemo(memo.id, memo.text, memo.colorTheme.color, memo.colorTheme.textColor, memo.attr.isPin)

        override fun updateMemoText(memoid: Long, text: String) = dao.updateMemoText(memoid, text)
        override fun getEditMemoById(memoid: Long): LiveData<EditMemoView> = dao.findMemoById(memoid)
        override fun insertMemo(memo: Memo): Long? = dao.insert(memo).firstOrNull()
        override fun insertMemos(memos: List<Memo>): List<Long> = dao.insert(memos)
        override fun updateColorTheme(memoId: Long, colorTheme: ColorTheme) {
            val (label, color, textColor) = colorTheme
            return dao.updateColorTheme(label, color, textColor, memoId)
        }
    }
}
