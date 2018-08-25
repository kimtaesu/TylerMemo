package com.hucet.tyler.memo.ui.add

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.Memo
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
    private lateinit var robot: AddMemoViewRobot
    @Before
    fun setUp() {
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        presenter = AddMemoPresenter(db)
        robot = AddMemoViewRobot(presenter)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Create memo events`() {
        val expectMemo = Memo("", "")
        val robot = AddMemoViewRobot(presenter)
        robot.assertViewStateRendered({
            //            when
            robot.createMemoIntent(expectMemo)
        }, {
            //            then
            listOf(AddMemoState(), AddMemoState(addMemo = expectMemo))
        })
    }

}