package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.*
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldContain
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
    private lateinit var observer: Observer<List<MemoView>>
    @Captor
    private lateinit var captor: ArgumentCaptor<List<MemoView>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = MemoRepository(db)
        reset(observer)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `insert memos to db`() {
        val memos = listOf(Memo("1"),
                Memo("12", MemoAttribute(false), ColorTheme("a", 1, 1)))
        repository.insertMemos(memos)

        repository.searchMemos("").observeForever(observer)

        verify(observer).onChanged(captor.capture())
        assertEquals(captor.value.map { it.memo }, memos)
    }

    @Test
    fun `en search text memo`() {
        val expect = Memo("abc")
        assertSearch(listOf(
                expect,
                Memo("asd")
        ), listOf(expect), "bc")
    }

    @Test
    fun `ko search text memo`() {
        val expect = Memo("하하")
        assertSearch(listOf(
                expect,
                Memo("asd")
        ), listOf(expect), "하하")
    }

    private fun assertSearch(memos: List<Memo>, expect: List<Memo>, keyword: String) {
        repository.insertMemos(memos)
        repository.searchMemos(keyword).observeForever(observer)

        verify(observer).onChanged(captor.capture())
        Assert.assertThat(captor.value.map { it.memo }, `is`(expect))
        Assert.assertThat(captor.value.size, `is`(expect.size))
    }

    @Test
    fun `insert integration obsever`() {
        val memos = listOf(
                Memo("1", MemoAttribute(false)),
                Memo("2", MemoAttribute(false))
        )

        val checkItem = CheckItem("ddd", true, 2)

        // search memo observer 등록
        repository.searchMemos("").observeForever(observer)
        verify(observer).onChanged(captor.capture())

//        Insert a memo
        repository.insertMemos(memos)
//      두번째 observer 호출
        verify(observer, times(2)).onChanged(captor.capture())

        captor.value.map { it.memo } `should equal` memos

//        Insert a check item
        repository.insertCheckItems(listOf(checkItem))

//      세번째 observer 호출
        verify(observer, times(3)).onChanged(captor.capture())

        captor.value[1].checkItems!! shouldContain checkItem
    }

    @Test
    fun `change color theme`() {
        repository.insertMemos(listOf(Memo("1", MemoAttribute(false))))

        repository.searchMemos("").observeForever(observer)
        verify(observer).onChanged(captor.capture())

        Assert.assertThat(captor.value.first().memo.colorTheme, `is`(ColorTheme.Companion.Theme.WHITE.colorTheme))
        val result = captor.value.first()

        // 메모 Theme 변경 White -> Blue
        repository.updateMemos(listOf(result.memo.copy(colorTheme = ColorTheme.Companion.Theme.BLUE.colorTheme)))
        // observer 두번째 호출
        verify(observer, times(2)).onChanged(captor.capture())

        Assert.assertThat(captor.value.first().memo.colorTheme, `is`(ColorTheme.Companion.Theme.BLUE.colorTheme))
    }

    @Test
    fun `pin true 정렬 순위`() {
        val expect = Memo("Pin true1", MemoAttribute(true))
        val memos = listOf(
                Memo("1", MemoAttribute(false)),
                expect
        )
        repository.insertMemos(memos)

        repository.searchMemos("").observeForever(observer)
        verify(observer).onChanged(captor.capture())
        Assert.assertThat(captor.value.first().memo, `is`(expect))

        // Pin true 메모 추가
        val expect2 = Memo("Pin true2", MemoAttribute(true))
        repository.insertMemos(listOf(expect2))
        // observer 두번째 호출
        verify(observer, times(2)).onChanged(captor.capture())

        // 첫 번째는 마지막 추가한 메모
        Assert.assertThat(captor.value.first().memo, `is`(expect2))
    }

}