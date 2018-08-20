package com.hucet.tyler.memo.ui.color

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabelPresenter @Inject constructor(
        private val db: MemoDb
) : MviBasePresenter<LabelView, LabelState>() {
    override fun bindIntents() {
        val createColorLabel = intent(LabelView::createdLabel)
                .map {
                    PartialStateChanges.CreatedLabel(it)
                }

        val allIntentsObservable = createColorLabel
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(
                allIntentsObservable.scan(LabelState(), this::viewStateReducer).distinctUntilChanged(), LabelView::render)
    }

    private fun viewStateReducer(previousState: LabelState,
                                 partialChanges: PartialStateChanges): LabelState {
        return when (partialChanges) {
            is PartialStateChanges.CreatedLabel -> {
                previousState.copy(isEmpty = false, labels = previousState.labels.plus(partialChanges.label))
            }
        }
    }

    private sealed class PartialStateChanges {
        class CreatedLabel(val label: Label) : PartialStateChanges()
    }
}
