package com.hucet.tyler.memo.ui.main

import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import java.util.concurrent.CopyOnWriteArrayList

class MainPresenterRobot constructor(
        presenter: MainPresenter
) {
    private val renderEvents = CopyOnWriteArrayList<MainState>()
    private val createMemoSubject = PublishSubject.create<Any>()
    private val clearIntentsSubject = PublishSubject.create<Unit>()

    init {
        presenter.attachView(object : MainView {
            override fun clearIntents(): Observable<Unit> = clearIntentsSubject

            override fun createMemo(): Observable<Any> = createMemoSubject

            override fun render(state: MainState) {
                renderEvents.add(state)
            }
        })
    }

    fun createMemoIntent() {
        createMemoSubject.onNext(Any())
    }

    fun assertViewStateRendered(`when`: () -> Unit, then: (List<MainState>) -> Unit) {
        `when`()
        then(renderEvents)
    }
}
