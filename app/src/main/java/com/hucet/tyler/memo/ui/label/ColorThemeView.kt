package com.hucet.tyler.memo.ui.label

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable

interface ColorThemeView : MvpView {

    fun createdLabel(): Observable<ColorTheme>

    fun render(state: ColorThemeState)
}