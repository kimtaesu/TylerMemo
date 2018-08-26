package com.hucet.tyler.memo.ui.add

import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import java.util.concurrent.CopyOnWriteArrayList

class AddMemoViewRobot constructor(
        presenter: AddMemoPresenter
) {
    private val renderEvents = CopyOnWriteArrayList<AddMemoState>()
    private val createMemoSubject = PublishSubject.create<Memo>()

    init {
        presenter.attachView(object : AddMemoView {
            override fun saveMemo(): Observable<MemoView> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun typingText(): Observable<CharSequence> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun render(state: AddMemoState) {
                renderEvents.add(state)
            }
        })
    }

    fun createMemoIntent(memo: Memo) {
        createMemoSubject.onNext(memo)
    }

    fun assertViewStateRendered(`when`: () -> Unit, then: () -> List<AddMemoState>): List<AddMemoState> {
        System.out.println("when")
        `when`()
        val expectStates = then()

        val eventCount = expectStates.size

        System.out.println("then")
        Assert.assertEquals(expectStates, renderEvents)
        return expectStates
    }
}
