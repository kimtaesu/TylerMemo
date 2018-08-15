package com.hucet.tyler.memo.ui.list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.Memo
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.*
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
class MemoRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: MemoRepository

    private lateinit var db: MemoDb

    @Mock
    private lateinit var observer: Observer<List<Memo>>

    @Captor
    private lateinit var captor: ArgumentCaptor<List<Memo>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = MemoRepository(db)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `insert memos to db`() {
        val memos = listOf(Memo("test", "1"), Memo("test2", "12"))
        repository.inserMemos(memos)

        repository.searchMemos("").observeForever(observer)

        verify(observer).onChanged(captor.capture())
        Assert.assertEquals(captor.value, memos)
    }
}