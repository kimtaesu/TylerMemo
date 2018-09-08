package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.dto.CheckableLabelView
import com.hucet.tyler.memo.repository.label.LabelRepository
import com.nhaarman.mockito_kotlin.reset
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class LabelRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: LabelRepository

    private lateinit var db: MemoDb

    @Mock
    private lateinit var observer: Observer<List<CheckableLabelView>>
    @Captor
    private lateinit var captor: ArgumentCaptor<List<CheckableLabelView>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
//        repository = LabelRepository(db)
        reset(observer)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_case() {

    }
}
