package com.hucet.tyler.memo.ui.add

import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.ui.main.MainPresenter
import com.hucet.tyler.memo.ui.main.MainState
import com.hucet.tyler.memo.ui.main.MainView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.TimeUnit

class AddMemoPresenterRobot constructor(
        presenter: AddMemoPresenter
) {
    private val renderEvents = CopyOnWriteArrayList<AddMemoState>()
    private val saveMemo = PublishSubject.create<Any>()
    private val fetchEditMemo = PublishSubject.create<EditMemoView>()
    private val typingText = PublishSubject.create<CharSequence>()
    private val createCheckItem = PublishSubject.create<CheckItem>()
    private val viewCheckItems = PublishSubject.create<Boolean>()
    private val colorThemeChanged = PublishSubject.create<ColorTheme>()

    init {
        presenter.attachView(object : AddMemoView {
            override fun saveMemo(): Observable<Any> = saveMemo

            override fun viewCheckItems(): Observable<Boolean> = viewCheckItems

            override fun createCheckItem(): Observable<CheckItem> = createCheckItem

            override fun fetchEditMemo(): Observable<EditMemoView> = fetchEditMemo

            override fun typingText(): Observable<CharSequence> = typingText

            override fun colorThemeChanged(): Observable<ColorTheme> = colorThemeChanged

            override fun render(state: AddMemoState) {
                renderEvents.add(state)
            }
        })
    }

    fun fetchEditMemo(editMemoView: EditMemoView) = fetchEditMemo.onNext(editMemoView)

    fun colorThemeChangedIntent(colorTheme: ColorTheme) = colorThemeChanged.onNext(colorTheme)

    fun saveMemoIntent() = saveMemo.onNext(Unit)

    fun typingTextIntent(text: String) = typingText.onNext(text)

    fun createCheckItem(checkItem: CheckItem) = createCheckItem.onNext(checkItem)

    fun assertViewStateRendered(`when`: () -> Unit, then: (List<AddMemoState>) -> Unit) {
        `when`()
        then(renderEvents)
    }

}
