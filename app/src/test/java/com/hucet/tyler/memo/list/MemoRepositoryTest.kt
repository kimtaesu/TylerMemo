package com.hucet.tyler.memo.list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.fixture.DBFixture
import com.hucet.tyler.memo.utils.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.Memo
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class MemoRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: MemoRepository

    private val db = DBFixture.createDatabase()

    @Mock
    private lateinit var observer: Observer<List<Memo>>

    @Captor
    private lateinit var captor: ArgumentCaptor<List<Memo>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MemoRepository(db.memoDao())
    }

    @Test
    fun `insert memos to db`() {
        val memos = listOf(Memo("test", "1"), Memo("test2", "12"))
        repository.inserMemos(memos)

        repository.allMemos().observeForever(observer)

        verify(observer).onChanged(captor.capture())
        Assert.assertEquals(captor.value, eq(memos))
    }
}