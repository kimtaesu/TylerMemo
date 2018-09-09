package com.hucet.tyler.memo.ui.add

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.utils.TestUtils
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class AddMemoPresenterTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var presenter: AddMemoPresenter

    private lateinit var db: MemoDb
    private lateinit var repository: MemoLabelRepository
    private lateinit var robot: AddMemoPresenterRobot

    private val context = RuntimeEnvironment.application
    @Before
    fun setUp() {
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = TestUtils.createMemoLabelRepository(db)
        presenter = AddMemoPresenter(repository)
        robot = AddMemoPresenterRobot(presenter)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `save memo`() {
        val observer = mock<Observer<EditMemoView>>()
        val captor = argumentCaptor<EditMemoView>()
        repository.getEditMemoById(1).observeForever(observer)

        val expect = EditMemoView(Memo("1", id = 1), arrayListOf(CheckItem("c1", false, 1, id = 1)))
        repository.insertMemo(expect.memo)
        repository.insertCheckItems(expect.checkItems!!)

        /**
         * Senario 1
         * Save memo
         * 1. Fetch a edit memo
         * 2. Change a color theme
         */
        val changedColorTheme = ColorTheme.Companion.Theme.BLUE.colorTheme
        val expectText = "aasdassszaa"
        robot.assertViewStateRendered({
            //            when
            robot.fetchEditMemo(expect)
            robot.colorThemeChangedIntent(changedColorTheme)
            robot.typingTextIntent("aas")
            robot.typingTextIntent(expectText)
            robot.saveMemoIntent()
        }, { result ->
            //            then
            result.size `should equal` 3
            result[0] `should equal` AddMemoState()
            result[1] `should equal` AddMemoState(editMemoView = expect)
            result[2] `should equal` AddMemoState(editMemoView = expect)

            verify(observer, times(4)).onChanged(captor.capture())
            captor.lastValue.memo.text `should equal` expectText
            captor.lastValue.memo.colorTheme = changedColorTheme
        })
        @Test
        fun `create check item`() {
            /**
             * Senario 4
             * create check item
             */
            val checkItem = CheckItem("11", false, 1, id = 11)
            robot.assertViewStateRendered({
                //            when
                robot.createCheckItem(checkItem)
            }, { result ->
                //            then
                result.size `should equal` 2
                val state = result.last()
                state.editMemoView?.checkItems `should equal` listOf(checkItem)
            })
        }
    }
}