package com.hucet.tyler.memo.ui.label

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable

interface ColorLabelView : MvpView {

    fun createdLabel(): Observable<Label>

    fun render(state: ColorLabelState)
}