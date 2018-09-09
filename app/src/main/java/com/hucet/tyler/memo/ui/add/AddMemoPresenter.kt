package com.hucet.tyler.memo.ui.add

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.ui.main.MainState
import com.hucet.tyler.memo.ui.main.MainView
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface AddMemoView : MvpView {
    fun checkItemsView(): Observable<Long>
    fun render(state: MainState)
}


@Singleton
@OpenForTesting
class AddMemoPresenter @Inject constructor(
        private val context: Context,
        private val repository: MemoRepository
) : MviBasePresenter<AddMemoView, AddMemoState>() {
    override fun bindIntents() {
        val checkItemsView = intent(AddMemoView::checkItemsView)
                .map {  }
    }

    private sealed class AddMemoPartState {

    }
}

data class AddMemoState(
        val checkItems: List<CheckItem>
)