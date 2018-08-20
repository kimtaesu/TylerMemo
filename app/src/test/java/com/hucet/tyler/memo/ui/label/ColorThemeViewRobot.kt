package com.hucet.tyler.memo.ui.label

import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Assert
import java.util.concurrent.CopyOnWriteArrayList

class ColorThemeViewRobot constructor(
        presenter: ColorLabelPresenter
) {
    private val renderEvents = CopyOnWriteArrayList<ColorThemeState>()
    private val createLabelSubject = PublishSubject.create<ColorTheme>()

    init {
        presenter.attachView(object : ColorThemeView {
            override fun createdLabel(): Observable<ColorTheme> = createLabelSubject

            override fun render(state: ColorThemeState) {
                renderEvents.add(state)
            }
        })
    }

    fun createColorThemeIntent(colorTheme: ColorTheme) {
        createLabelSubject.onNext(colorTheme)
    }

    fun assertViewStateRendered(`when`: () -> Unit, then: () -> List<ColorThemeState>): List<ColorThemeState> {
        System.out.println("when")
        `when`()
        val expectStates = then()

        val eventCount = expectStates.size

        System.out.println("then")
        Assert.assertEquals(expectStates, renderEvents)
        return expectStates
    }
}
