package com.hucet.tyler.memo.ui.label

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.repository.LabelRepository
import com.hucet.tyler.memo.repository.MemoLabelRepository
import com.hucet.tyler.memo.utils.AppExecutors
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
        private val memoLabelRepository: MemoLabelRepository,
        private val labelRepository: LabelRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val labelSearch = MutableLiveData<LabelSearch>()

    private val labelResult = Transformations.map(labelSearch) {
        memoLabelRepository.searchCheckedLabels(it.memoId, it.keyword)
    }

    private val labels = Transformations.switchMap(labelResult) {
        it
    }

    val fetchLabels = Transformations.map(labels) {
        val search = labelSearch.value

        val keyword = search?.keyword
        val items = it ?: emptyList()
        val newKeyword = if (items.firstOrNull { it.label == keyword } == null && keyword?.isEmpty() == false)
            keyword
        else
            null
        it to newKeyword
    }

    fun bindSearch(searchView: Observable<CharSequence>, memoId: Long) {
        searchView
                .observeOn(Schedulers.io())
                .map { it.toString() }
                .subscribe {
                    if (labelSearch.value?.keyword != it) {
                        labelSearch.postValue(LabelSearch(it, memoId))
                    }
                }
                .also { compositeDisposable.add(it) }
    }

    fun createLabel(keyword: String, memoId: Long) {
        Observable
                .fromCallable {
                    val labelId = labelRepository.insertLabel(Label(keyword))
                    labelId?.run {
                        memoLabelRepository.insertMemoLabelJoin(MemoLabelJoin(memoId, labelId))
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .also { compositeDisposable.add(it) }
    }

    fun createMemoLabel(memoId: Long, labelId: Long) {
        Observable
                .fromCallable {
                    memoLabelRepository.insertMemoLabelJoin(MemoLabelJoin(memoId, labelId))
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .also { compositeDisposable.add(it) }
    }

    fun deleteMemoLabel(memoId: Long, labelId: Long) {
        Observable
                .fromCallable { memoLabelRepository.deleteById(memoId, labelId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .also { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}