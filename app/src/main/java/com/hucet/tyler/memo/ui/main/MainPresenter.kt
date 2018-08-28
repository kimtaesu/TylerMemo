package com.hucet.tyler.memo.ui.main

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


interface MainView : MvpView {
    fun clearIntents(): Observable<Unit>
    fun createMemo(): Observable<Any>
    fun render(state: MainState)
}

@Singleton
@OpenForTesting
class MainPresenter @Inject constructor(
        private val context: Context,
        private val repository: MemoRepository
) : MviBasePresenter<MainView, MainState>() {
    override fun bindIntents() {
        val clearIntents = intent(MainView::clearIntents).map { MainState.ClearIntents }

        val createMemo = intent(MainView::createMemo)
                .doOnNext { Timber.d("intent: created memo $it") }
                .observeOn(Schedulers.io())
                .flatMap<MainState> {
                    val memo = Memo.empty()
                    val memoId = repository.insertMemo(memo) ?: throw IllegalArgumentException()
                    Observable.fromCallable {
                        MainState.CreatedMemo(memo.apply {
                            id = memoId
                        })
                    }
                }
                .onErrorReturn { MainState.FailCreateMemo(context.getString(R.string.fail_create_memo_msg)) }

        val allIntentsObservable = Observable.merge(clearIntents, createMemo).distinctUntilChanged().observeOn(AndroidSchedulers.mainThread());
        subscribeViewState(allIntentsObservable, MainView::render)
    }
}

sealed class MainState {
    data class CreatedMemo(val memo: Memo) : MainState()
    data class FailCreateMemo(val errorMsg: String) : MainState()
    object ClearIntents : MainState()
}