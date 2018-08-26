package com.hucet.tyler.memo.ui.add

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface AddMemoView : MvpView {
    fun saveMemo(): Observable<MemoView>
    fun typingText(): Observable<CharSequence>
    fun render(state: AddMemoState)
}

@Singleton
class AddMemoPresenter @Inject constructor(
        private val repository: MemoRepository) : MviBasePresenter<AddMemoView, AddMemoState>() {
    override fun bindIntents() {
        val typingText = intent(AddMemoView::typingText)
                .map {
                    PartialStateChanges.TypingText(it.toString())
                }
        val saveMemo = intent(AddMemoView::saveMemo)
                .observeOn(Schedulers.io())
                .map {
                    repository.insertMemo(it.memo)
                }
                .map {
                    PartialStateChanges.SaveMemo(it)
                }
        val allIntentsObservable = Observable.merge(typingText, saveMemo).observeOn(AndroidSchedulers.mainThread())


        subscribeViewState(
                allIntentsObservable.scan(AddMemoState(), this::viewStateReducer), AddMemoView::render)
    }

    private fun viewStateReducer(previousState: AddMemoState,
                                 partialChanges: PartialStateChanges): AddMemoState {
        return when (partialChanges) {
            is PartialStateChanges.SaveMemo -> {
                previousState.memoView?.memo?.id = partialChanges.memoId
                previousState.copy(isInitSavedMemo = true)
            }
            is PartialStateChanges.ChangedColorTheme -> {
                previousState.memoView?.memo?.colorTheme = partialChanges.colorTheme
                previousState
            }
            is PartialStateChanges.TypingText -> {
                previousState.memoView?.memo?.text = partialChanges.text
                previousState
            }
        }
    }
}

private sealed class PartialStateChanges {
    class TypingText(val text: String) : PartialStateChanges()
    class SaveMemo(val memoId: Long) : PartialStateChanges()
    class ChangedColorTheme(val colorTheme: ColorTheme) : PartialStateChanges()
}


data class AddMemoState(
        var memoView: MemoView? = MemoView(),
        val isInitSavedMemo: Boolean = false
)