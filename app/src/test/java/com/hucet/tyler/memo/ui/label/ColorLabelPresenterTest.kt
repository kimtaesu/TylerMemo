package com.hucet.tyler.memo.ui.label

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hucet.tyler.memo.db.LabelDao
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.Label
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
class ColorLabelPresenterTest {
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
        val createdLabel = Label("t", "t")
        val robot = ColorLabelViewRobot(presenter)
        robot.assertViewStateRendered({
            //            when
        }, {
            //            then
            listOf(ColorLabelState(LabelDao.populate(), true))
        })

         robot.assertViewStateRendered({
            //            when
            robot.createLabelIntent(createdLabel)
        }, {
            //            then
            listOf(ColorLabelState(LabelDao.populate(), true),
                    ColorLabelState(LabelDao.populate().plus(createdLabel), true)
            )
        })
    }
}