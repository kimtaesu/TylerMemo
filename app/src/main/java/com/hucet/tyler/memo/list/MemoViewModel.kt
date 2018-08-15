package com.hucet.tyler.memo.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Singleton

import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import com.hucet.tyler.memo.OpenForTesting

@OpenForTesting
@Singleton
class MemoViewModel @Inject constructor(
        private val repository: MemoRepository
) : ViewModel() {
    private val keywordName = MutableLiveData<String>()

    private val memoResult = map(keywordName, {
        repository.searchMemos(it)
    })

    val fetchMemos = switchMap(memoResult, { it })

    fun showMemos(keyword: String): Boolean {
        if (keywordName.value == keyword) {
            return false
        }
        keywordName.value = keyword
        return true
    }
}