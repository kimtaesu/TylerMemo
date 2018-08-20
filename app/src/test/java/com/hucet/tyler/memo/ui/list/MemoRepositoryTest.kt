package com.hucet.tyler.memo.ui.list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.Matchers
import org.hamcrest.collection.IsArrayContaining
import org.junit.*
import org.junit.Assert.*
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

    @Mock
    private lateinit var observerMemoView: Observer<List<MemoView>>
    @Captor
    private lateinit var captorMemoView: ArgumentCaptor<List<MemoView>>

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
        val memos = listOf(Memo("test", "1"),
                Memo("test2", "12", ColorTheme("a", 1)))
        repository.insertMemos(memos)

        repository.searchMemos("").observeForever(observer)

        verify(observer).onChanged(captor.capture())
        assertEquals(captor.value, memos)
    }

    @Test
    fun `retrive memoviews from db`() {
        val memos = listOf(
                Memo("test", "1"),
                Memo("test2", "12")
        )

        val label = Label("abc", 1)
        repository.insertMemos(memos)
        repository.insertLabels(label)
        repository.searchMemoViews("").observeForever(observerMemoView)

        verify(observerMemoView).onChanged(captorMemoView.capture())
        assertEquals(captorMemoView.value.map { it.memo }, memos)
        Assert.assertThat(captorMemoView.value.first().labels, hasItem(label))
    }
}