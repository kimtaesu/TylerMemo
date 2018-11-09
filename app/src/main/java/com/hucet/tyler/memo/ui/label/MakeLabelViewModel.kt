package com.hucet.tyler.memo.ui.label

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.common.DispoableViewModel
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.repository.label.LabelRepository
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class MakeLabelViewModel @Inject constructor(
        private val memoLabelRepository: MemoLabelRepository,
        private val labelRepository: LabelRepository
) : DispoableViewModel() {
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
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (labelSearch.value?.keyword != it) {
                        labelSearch.value = LabelSearch(it, memoId)
                    }
                }
                .also { compositeDisposable.add(it) }
    }

    fun createLabel(keyword: String, memoId: Long) {
        launch(parent = parentJob) {
            val labelId = labelRepository.insertLabel(Label(keyword))
            labelId?.run {
                memoLabelRepository.insertMemoLabelJoin(MemoLabelJoin(memoId, labelId))
            }
        }
    }

    fun createMemoLabel(memoId: Long, labelId: Long) {
        launch(parent = parentJob) {
            memoLabelRepository.insertMemoLabelJoin(MemoLabelJoin(memoId, labelId))
        }
    }

    fun deleteMemoLabel(memoId: Long, labelId: Long) {
        launch(parent = parentJob) {
            memoLabelRepository.deleteById(memoId, labelId)
        }
    }
}