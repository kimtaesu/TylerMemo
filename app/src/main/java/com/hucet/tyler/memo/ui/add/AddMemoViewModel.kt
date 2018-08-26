package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.repository.MemoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class AddMemoViewModel @Inject constructor(
        private val repository: MemoRepository
) : ViewModel() {
    fun findMemoViewById(id: Long): LiveData<MemoView> {
        return repository.findMemoById(id)
    }
}