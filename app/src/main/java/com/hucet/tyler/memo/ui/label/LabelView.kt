package com.hucet.tyler.memo.ui.color

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable

interface LabelView : MvpView {

    fun createdLabel(): Observable<Label>

    fun searchLabel(keyword: String): Observable<CharSequence>

    fun render(state: LabelState)
}