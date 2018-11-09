package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.DispoableViewModel
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

data class AddMemoViewIntent(
        val typingText: Observable<String>,
        val changeColorTheme: Observable<ColorTheme>,
        val saveMemo: Observable<Any>
)

@Singleton
@OpenForTesting
class AddMemoViewModel @Inject constructor(
        private val repository: MemoLabelRepository
) : DispoableViewModel() {
    fun getLabelByMemo(memoId: Long): LiveData<List<Label>> = repository.getLabelByMemo(memoId)

    fun getEditMemoById(memoId: Long) = repository.getEditMemoById(memoId)

    fun bind(intent: AddMemoViewIntent) {
    }
}