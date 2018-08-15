package com.hucet.tyler.memo.ui.label

import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import org.hamcrest.core.Is
import org.junit.Assert
import java.util.concurrent.CopyOnWriteArrayList

class ColorLabelViewRobot constructor(
        presenter: ColorLabelPresenter
) {
    private val renderEvents = CopyOnWriteArrayList<ColorLabelState>()
    private val createLabelSubject = PublishSubject.create<Label>()

    init {
        presenter.attachView(object : ColorLabelView {
            override fun createdLabel(): Observable<Label> = createLabelSubject

            override fun render(state: ColorLabelState) {
                renderEvents.add(state)
            }
        })
    }

    fun createLabelIntent(label: Label) {
        createLabelSubject.onNext(label)
    }

    fun assertViewStateRendered(`when`: () -> Unit, then: () -> List<ColorLabelState>): List<ColorLabelState> {
        System.out.println("when")
        `when`()
        val expectStates = then()

        val eventCount = expectStates.size

        System.out.println("then")
        Assert.assertEquals(expectStates, renderEvents)
        return expectStates
    }
}
