package com.hucet.tyler.memo.ui.label

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.ColorTheme
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
class ColorThemePresenterTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var presenter: ColorLabelPresenter

    private lateinit var db: MemoDb
    @Before
    fun setUp() {
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        presenter = ColorLabelPresenter(db)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `createLabel events`() {
        val robot = ColorThemeViewRobot(presenter)
        robot.assertViewStateRendered({
            //            when
        }, {
            //            then
            listOf(ColorThemeState(ColorTheme.generate()))
        })


        val createdColorTheme = ColorTheme("a", 1)
        robot.assertViewStateRendered({
            //            when
            robot.createColorThemeIntent(createdColorTheme)
        }, {
            //            then
            listOf(ColorThemeState(ColorTheme.generate()),
                    ColorThemeState(ColorTheme.generate().plus(createdColorTheme))
            )
        })
    }
}