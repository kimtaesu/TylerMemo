package com.hucet.tyler.memo.ui.label

import android.graphics.Color
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.utils.toSingle
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorLabelPresenter @Inject constructor(
        private val db: MemoDb
) : MviBasePresenter<ColorThemeView, ColorThemeState>() {
    override fun bindIntents() {
        val createColorLabel = intent(ColorThemeView::createdLabel)
                .map {
                    PartialStateChanges.CreatedLabel(it)
                }

        val allIntentsObservable = createColorLabel
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(
                allIntentsObservable.scan(ColorThemeState(
                        colorThemes = ColorTheme.generate()
                ), this::viewStateReducer).distinctUntilChanged(), ColorThemeView::render)
    }

    private fun viewStateReducer(previousState: ColorThemeState,
                                 partialChanges: PartialStateChanges): ColorThemeState {
        return when (partialChanges) {
            is PartialStateChanges.CreatedLabel -> {
                val items = previousState.colorThemes?.plus(partialChanges.color)
                previousState.copy(colorThemes = items)
            }
        }
    }
}

sealed class PartialStateChanges {
    class CreatedLabel(val color: ColorTheme) : PartialStateChanges()
}