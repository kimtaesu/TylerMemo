package com.hucet.tyler.memo.db

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.hucet.tyler.memo.ui.list.MemoRepository
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.utils.toObservable
import org.hamcrest.core.Is
import org.hamcrest.core.Is.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class LabelDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var db: MemoDb

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `initially populate label data`() {
        val actual = db.labelDao().all().toObservable().test().values().flatMap { it }
        assertEquals(actual, LabelDao.populate())
    }
}