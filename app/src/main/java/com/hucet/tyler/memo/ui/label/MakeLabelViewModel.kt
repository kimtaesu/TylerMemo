package com.hucet.tyler.memo.ui.label

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.repository.LabelRepository
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class MakeLabelViewModel @Inject constructor(
        private val repository: LabelRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val keywordName = MutableLiveData<String>()

    private val labelResult = Transformations.map(keywordName) {
        repository.searchCheckedLabels(it)
    }

    private val labels = Transformations.switchMap(labelResult) {
        it
    }

    val fetchLabels = Transformations.map(labels) {
        keywordName.value to it
    }

    fun bindSearch(searchView: Observable<CharSequence>) {
        searchView
                .observeOn(Schedulers.io())
                .map { it.toString() }
                .subscribe {
                    if (keywordName.value != it) {
                        keywordName.postValue(it)
                    }
                }
                .also { compositeDisposable.add(it) }
    }

    fun createLabel(keyword: String, id: Long, consumer: Consumer<in Unit>) {
        Observable
                .fromCallable { repository.insertLabel(Label(keyword, id)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
                .also { compositeDisposable.add(it) }
    }

    fun updateLabel(label: Label) {

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}