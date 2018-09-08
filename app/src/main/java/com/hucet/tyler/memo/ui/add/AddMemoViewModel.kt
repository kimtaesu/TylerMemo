package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.DispoableViewModel
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class AddMemoViewModel @Inject constructor(
        private val memoLabelRepository: MemoLabelRepository,
        private val memoRepository: MemoRepository
) : DispoableViewModel() {
    fun findMemoViewById(memoId: Long): LiveData<List<Label>> =
            memoLabelRepository.getLabelByMemo(memoId)

    fun updateMemo(memo: Memo) {
        launch(parentJob) {
            //            memoRepository.updateMemos(listOf(memo))
        }
    }
}