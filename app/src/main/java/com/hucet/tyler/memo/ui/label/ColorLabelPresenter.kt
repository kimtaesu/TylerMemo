package com.hucet.tyler.memo.ui.label

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.utils.toObservable
import com.hucet.tyler.memo.utils.toSingle
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorLabelPresenter @Inject constructor(
        private val db: MemoDb
) : MviBasePresenter<ColorLabelView, ColorLabelState>() {
    override fun bindIntents() {
        val createColorLabel = intent(ColorLabelView::createdLabel)
                .map {
                    PartialStateChanges.CreatedLabel(it)
                }

        val initFetchColorLabel = db.labelDao().all().toSingle()
                .map {
                    PartialStateChanges.InitFetchLabel(it)
                }.toObservable()

        val allIntentsObservable = Observable.merge(createColorLabel, initFetchColorLabel)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(
                allIntentsObservable.scan(ColorLabelState(), this::viewStateReducer).distinctUntilChanged(), ColorLabelView::render)
    }

    private fun viewStateReducer(previousState: ColorLabelState,
                                 partialChanges: PartialStateChanges): ColorLabelState {
        return when (partialChanges) {
            is PartialStateChanges.CreatedLabel -> {
                val items = previousState.labels?.plus(partialChanges.label)
                previousState.copy(isNeedUpdate = true, labels = items)
            }
            is PartialStateChanges.InitFetchLabel -> {
                previousState.copy(isNeedUpdate = true, labels = partialChanges.labels
                        ?: emptyList())
            }
        }
    }
}

sealed class PartialStateChanges {
    class CreatedLabel(val label: Label) : PartialStateChanges()
    class InitFetchLabel(val labels: List<Label>?) : PartialStateChanges()
}