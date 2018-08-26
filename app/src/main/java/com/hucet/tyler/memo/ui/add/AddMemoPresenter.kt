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
    fun saveMemo(): Observable<Memo>
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
        val allIntentsObservable = typingText.observeOn(AndroidSchedulers.mainThread())


        subscribeViewState(
                allIntentsObservable.scan(AddMemoState(), this::viewStateReducer), AddMemoView::render)
    }

    private fun viewStateReducer(previousState: AddMemoState,
                                 partialChanges: PartialStateChanges): AddMemoState {
        return when (partialChanges) {
            is PartialStateChanges.TypingText -> {
                previousState.memo?.text = partialChanges.text
                previousState
            }
        }
    }
}

private sealed class PartialStateChanges {
    class TypingText(val text: String) : PartialStateChanges()
}


data class AddMemoState(
        var memo: Memo? = Memo.empty()
)