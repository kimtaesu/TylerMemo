package com.hucet.tyler.memo.list

import com.hannesdorfmann.mosby3.mvp.MvpView

interface MemoListView : MvpView {
    fun render(state: MemoState)
}