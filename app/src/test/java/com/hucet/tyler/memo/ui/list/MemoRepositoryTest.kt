package com.hucet.tyler.memo.ui.list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.*
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.Matcher
import org.hamcrest.core.Is
import org.hamcrest.core.Is.*
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
import org.hamcrest.CoreMatchers


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
                Memo("test2", "12", MemoAttribute(false), ColorTheme("a", 1)))
        repository.insertMemos(memos)

        repository.searchMemos("").observeForever(observer)

        verify(observer).onChanged(captor.capture())
        assertEquals(captor.value, memos)
    }

    @Test
    fun `en search subject memo`() {
        val expect = Memo("abc", "qwe")
        assertSearch(listOf(
                expect,
                Memo("sss", "asd")
        ), listOf(expect), "b")
    }

    @Test
    fun `en search text memo`() {
        val expect = Memo("abc", "abc")
        assertSearch(listOf(
                expect,
                Memo("sss", "asd")
        ), listOf(expect), "bc")
    }

    @Test
    fun `ko search subject memo`() {
        val expect = Memo("하하", "ㅁㄴㅇ")
        assertSearch(listOf(
                expect,
                Memo("sss", "asd")
        ), listOf(expect), "하하")
    }

    private fun assertSearch(memos: List<Memo>, expect: List<Memo>, keyword: String) {
        repository.insertMemos(memos)
        repository.searchMemos(keyword).observeForever(observer)

        verify(observer).onChanged(captor.capture())
        Assert.assertThat(captor.value, `is`(expect))
        Assert.assertThat(captor.value.size, `is`(expect.size))
    }

    @Test
    fun `retrive memoviews from db`() {
        val memos = listOf(
                Memo("test", "1", MemoAttribute(false)),
                Memo("test2", "12", MemoAttribute(false))
        )

        val label = Label("abc", 1)
        val checkItem = CheckItem("ddd", true, 2)

        repository.insertMemos(memos)
        repository.insertLabels(listOf(label))

        repository.searchMemoViews("").observeForever(observerMemoView)

        verify(observerMemoView).onChanged(captorMemoView.capture())

        assertEquals(captorMemoView.value.map { it.memo }, memos)
        assertThat(captorMemoView.value[0].labels, hasItem(label))

        // 추가적으로 INSERT CheckItem
        repository.insertCheckItems(listOf(checkItem))
        // observerMemoView 두번째 호출
        verify(observerMemoView, times(2)).onChanged(captorMemoView.capture())
        assertThat(captorMemoView.value[1].checkItems, hasItem(checkItem))
    }

    @Test
    fun `change color theme`() {
        repository.insertMemos(listOf(Memo("test", "1", MemoAttribute(false))))

        repository.searchMemos("").observeForever(observer)
        verify(observer).onChanged(captor.capture())

        Assert.assertThat(captor.value.first().colorTheme, `is`(ColorTheme.Companion.Theme.WHITE.colorTheme))
        val result = captor.value.first()

        result.colorTheme = ColorTheme.Companion.Theme.BLUE.colorTheme

        // 메모 Theme 변경 White -> Blue
        repository.updateMemos(listOf(result))
        // observer 두번째 호출
        verify(observer, times(2)).onChanged(captor.capture())

        Assert.assertThat(captor.value.first().colorTheme, `is`(ColorTheme.Companion.Theme.BLUE.colorTheme))
    }
}