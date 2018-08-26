package com.hucet.tyler.memo.ui.add

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface AddMemoView : MvpView {
    fun createMemo(): Observable<Memo>
    fun changeColorTheme(): Observable<ColorTheme>
    fun render(state: AddMemoState)
}

@Singleton
class AddMemoPresenter @Inject constructor(private val db: MemoDb) : MviBasePresenter<AddMemoView, AddMemoState>() {
    override fun bindIntents() {
        val createMemo = intent(AddMemoView::createMemo)
                .observeOn(Schedulers.io())
                .map {
                    db.memoDao().insert(listOf(it))
                    it
                }
                .map {
                    PartialStateChanges.CreatedMemo(it)
                }
        val changedColorTheme = intent(AddMemoView::changeColorTheme)
                .observeOn(Schedulers.io())
                .map {
                    PartialStateChanges.ChangedColorTheme(it)
                }
        val allIntentsObservable = Observable.merge(createMemo, changedColorTheme).observeOn(AndroidSchedulers.mainThread())


        subscribeViewState(
                allIntentsObservable.scan(AddMemoState(), this::viewStateReducer).distinctUntilChanged(), AddMemoView::render)
    }

    private fun viewStateReducer(previousState: AddMemoState,
                                 partialChanges: PartialStateChanges): AddMemoState {
        return when (partialChanges) {
            is PartialStateChanges.CreatedMemo -> {
                previousState.copy(createdMemo = partialChanges.memo, changedColorTheme = null)
            }
            is PartialStateChanges.ChangedColorTheme -> {
                previousState.copy(createdMemo = null, changedColorTheme = partialChanges.colorTheme)
            }
        }
    }
}

private sealed class PartialStateChanges {
    class CreatedMemo(val memo: Memo) : PartialStateChanges()
    class ChangedColorTheme(val colorTheme: ColorTheme) : PartialStateChanges()
}


data class AddMemoState(
        val createdMemo: Memo? = null,

        val changedColorTheme: ColorTheme? = null
)