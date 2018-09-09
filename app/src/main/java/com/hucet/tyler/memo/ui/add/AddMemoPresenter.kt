package com.hucet.tyler.memo.ui.add

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import com.hucet.tyler.memo.ui.main.MainState
import com.hucet.tyler.memo.ui.main.MainView
import com.hucet.tyler.memo.utils.toFlowable
import com.hucet.tyler.memo.utils.toObservable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface AddMemoView : MvpView {
    fun saveMemo(): Observable<Any>
    fun viewCheckItems(): Observable<Boolean>
    fun createCheckItem(): Observable<CheckItem>
    fun fetchEditMemo(): Observable<EditMemoView>
    fun typingText(): Observable<CharSequence>
    fun colorThemeChanged(): Observable<ColorTheme>
    fun render(state: AddMemoState)
}


@Singleton
@OpenForTesting
class AddMemoPresenter @Inject constructor(
        private val repository: MemoLabelRepository
) : MviBasePresenter<AddMemoView, AddMemoState>() {
    override fun bindIntents() {
        val fetchEditMemo = intent(AddMemoView::fetchEditMemo)
        val typingText = intent(AddMemoView::typingText)
        val changeColorTheme = intent(AddMemoView::colorThemeChanged)

        val memoIntents = listOf(fetchEditMemo, typingText, changeColorTheme)

        val combineMemoIntent = Observable.combineLatest(memoIntents) {
            val editMemo = it[0] as EditMemoView
            val typingText = it[1] as CharSequence
            val colorTheme = it[2] as ColorTheme

            editMemo.memo.text = typingText.toString()
            editMemo.memo.colorTheme = colorTheme
            editMemo.memo
        }

        val saveMemo = intent(AddMemoView::saveMemo)
                .observeOn(Schedulers.io())
                .withLatestFrom(combineMemoIntent, BiFunction { _: Any, t2: Memo -> t2 })
                .doOnNext { Timber.d("intent: save memo ${it}") }
                .map {
                    repository.updateMemo(it)
                    AddMemoPartState.Nothing
                }

        val fetchEditMemoIntent = fetchEditMemo
                .map {
                    AddMemoPartState.FetchEditMemo(it)
                }
        val createCheckItem = intent(AddMemoView::createCheckItem)
                .observeOn(Schedulers.io())
                .map {
                    repository.insertCheckItem(it)
                    AddMemoPartState.Nothing
                }


        val viewCheckItems = intent(AddMemoView::viewCheckItems)
                .observeOn(Schedulers.io())
                .map {
                    AddMemoPartState.ViewCheckItems(it)
                }

        val allIntentsObservable = Observable
                .merge(listOf(saveMemo, viewCheckItems, createCheckItem, fetchEditMemoIntent))
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(
                allIntentsObservable.scan<AddMemoState>(AddMemoState(), this::viewStateReducer), AddMemoView::render)
    }

    private fun viewStateReducer(previousState: AddMemoState, partialChanges: AddMemoPartState): AddMemoState {
        return when (partialChanges) {
            is AddMemoPresenter.AddMemoPartState.ViewCheckItems -> {
                AddMemoState(isShowCheckItems = partialChanges.isShow)
            }
            is AddMemoPresenter.AddMemoPartState.FetchEditMemo -> {
                AddMemoState(editMemoView = partialChanges.editMemoView)
            }
            AddMemoPresenter.AddMemoPartState.Nothing -> AddMemoState()
        }
    }

    private sealed class AddMemoPartState {
        class ViewCheckItems(val isShow: Boolean) : AddMemoPartState()
        class FetchEditMemo(val editMemoView: EditMemoView) : AddMemoPartState()
        object Nothing : AddMemoPartState()
    }
}

data class AddMemoState(
        val editMemoView: EditMemoView? = null,
        val isShowCheckItems: Boolean = false
)