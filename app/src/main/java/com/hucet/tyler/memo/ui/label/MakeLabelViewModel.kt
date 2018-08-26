package com.hucet.tyler.memo.ui.label

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.repository.LabelRepository
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class MakeLabelViewModel @Inject constructor(
        private val repository: LabelRepository,
        private val appExecutors: AppExecutors
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val keywordName = MutableLiveData<String>()

    private val labelResult = Transformations.map(keywordName) {
        repository.searchLabels(it)
    }

    private val labels = Transformations.switchMap(labelResult) {
        it
    }

    val fetchLabels = Transformations.map(labels) {
        keywordName.value to it
    }

    fun bindSearch(searchView: Observable<CharSequence>) {
        searchView
                .map { it.toString() }
                .subscribe {
                    if (keywordName.value != it) {
                        keywordName.postValue(it)
                    }
                }
                .also { compositeDisposable.add(it) }
    }

    fun createLabel(keyword: String, id: Long) {
        appExecutors.diskIO().execute {
            repository.insertLabel(Label(keyword, id))
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}