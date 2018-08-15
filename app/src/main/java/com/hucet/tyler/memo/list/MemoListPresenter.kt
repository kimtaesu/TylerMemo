package com.hucet.tyler.memo.list

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import javax.inject.Inject


class MemoListPresenter @Inject constructor() : MviBasePresenter<MemoListView, MemoState>() {
    override fun bindIntents() {
    }
}