package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.DispoableViewModel
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.MemoAttribute
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class AddMemoViewModel @Inject constructor(
        private val repository: MemoLabelRepository
) : DispoableViewModel() {
    fun findMemoViewById(memoId: Long): LiveData<List<Label>> =
            repository.getLabelByMemo(memoId)

    fun updateColorTheme(memoId: Long, colorTheme: ColorTheme) {
        launch(parentJob) {
            repository.updateColorTheme(memoId, colorTheme)
        }
    }

    fun updateMemo(memoId: Long, text: String, attr: MemoAttribute) {
        launch(parentJob) {
            repository.updateMemoText(memoId, text)
            repository.updateMemoAttr(memoId, attr)
        }
    }
}