package com.hucet.tyler.memo.list

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoListPresenter @Inject constructor() : MviBasePresenter<MemoListView, MemoState>() {
    override fun bindIntents() {
    }
}