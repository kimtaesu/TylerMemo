package com.hucet.tyler.memo.common

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.cancelAndJoin

abstract class ConcurrencyViewModel : ViewModel() {

    protected val parentJob = Job()
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        unsubscribe()
    }

    private fun unsubscribe() {
        parentJob.cancel()
        compositeDisposable.dispose()
    }
}