package com.hucet.tyler.memo.ui.color

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.vo.ColorTheme
import io.reactivex.Observable

interface ColorThemeView : MvpView {

    fun createdColor(): Observable<ColorTheme>

    fun render(state: ColorThemeState)
}