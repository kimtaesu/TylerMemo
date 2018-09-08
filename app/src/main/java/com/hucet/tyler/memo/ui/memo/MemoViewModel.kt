package com.hucet.tyler.memo.ui.memo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class MemoViewModel @Inject constructor(
        private val repository: MemoLabelRepository
) : ViewModel() {

    private val keywordName = MutableLiveData<String>()

//    private val memoResult = map(keywordName) {
//        repository.searchMemos(it)
//    }

//    val fetchMemos = switchMap(memoResult) { it }

    fun showMemos(keyword: String) {
        if (keywordName.value == keyword) {
            return
        }
        keywordName.value = keyword

    }
}