package com.hucet.tyler.memo.ui.color

import com.hucet.tyler.memo.vo.ColorTheme
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import java.util.concurrent.CopyOnWriteArrayList

class ColorThemeViewRobot constructor(
        presenter: ColorLabelPresenter
) {
    private val renderEvents = CopyOnWriteArrayList<LabelState>()
    private val createLabelSubject = PublishSubject.create<ColorTheme>()

    init {
        presenter.attachView(object : LabelView {
            override fun createdLabel(): Observable<ColorTheme> = createLabelSubject

            override fun render(state: LabelState) {
                renderEvents.add(state)
            }
        })
    }

    fun createColorThemeIntent(colorTheme: ColorTheme) {
        createLabelSubject.onNext(colorTheme)
    }

    fun assertViewStateRendered(`when`: () -> Unit, then: () -> List<LabelState>): List<LabelState> {
        System.out.println("when")
        `when`()
        val expectStates = then()

        val eventCount = expectStates.size

        System.out.println("then")
        Assert.assertEquals(expectStates, renderEvents)
        return expectStates
    }
}
