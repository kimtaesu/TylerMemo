package com.hucet.tyler.memo.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.db.model.Memo
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBeGreaterThan
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class MainPresenterTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var presenter: MainPresenter

    private lateinit var db: MemoDb
    private lateinit var repository: MemoRepository
    private lateinit var robot: MainPresenterRobot

    private val context = RuntimeEnvironment.application
    @Before
    fun setUp() {
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = MemoRepository.MemoRepositoryImpl(db.memoDao())
        presenter = MainPresenter(context, repository)
        robot = MainPresenterRobot(presenter)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Create memo events`() {

        val expectMemo = Memo.empty()
        robot.assertViewStateRendered({
            //            when
            robot.createMemoIntent()
        }, { result ->
            val memo = (result[0] as MainState.CreatedMemo).memo
            //            then
            result.size `should equal` 1
            memo.id shouldBeGreaterThan 0
            memo.text `should equal` expectMemo.text

        })
    }
}