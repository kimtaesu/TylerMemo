package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.*
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.*
import org.hamcrest.core.Is.*
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

    private lateinit var memoRepository: MemoRepository

    private lateinit var db: MemoDb

    @Mock
    private lateinit var observer: Observer<List<MemoView>>
    @Captor
    private lateinit var captor: ArgumentCaptor<List<MemoView>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        memoRepository = MemoRepository(db)
        reset(observer)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `en search text memo`() {
        val expect = Memo("abc", id = 3)
        memoRepository.searchMemos("bc").observeForever(observer)
        memoRepository.insertMemos(listOf(expect, Memo("asd", id = 1)))

        verify(observer, times(2)).onChanged(captor.capture())

        captor.value.size shouldEqual 1
        captor.value.first().memo shouldEqual expect
    }

    @Test
    fun `ko search text memo`() {
        val expect = Memo("하하", id = 3)
        memoRepository.searchMemos("하").observeForever(observer)
        memoRepository.insertMemos(listOf(expect, Memo("ㅁㄴㅇㄴㅇ", id = 1)))

        verify(observer, times(2)).onChanged(captor.capture())

        captor.value.size shouldEqual 1
        captor.value.first().memo shouldEqual expect
    }

    @Test
    fun `insert integration obsever`() {
        val memos = listOf(
                Memo("1", MemoAttribute(false), id = 1),
                Memo("2", MemoAttribute(false), id = 2)
        )

        val checkItem = CheckItem("ddd", true, 2, 1)

        // search memo observer 등록
        memoRepository.searchMemos("").observeForever(observer)
        verify(observer).onChanged(captor.capture())

//        Insert a memo
        memoRepository.insertMemos(memos)
//      두번째 observer 호출
        verify(observer, times(2)).onChanged(captor.capture())

        captor.value.map { it.memo } shouldContainAll memos

//        Insert a check item
        memoRepository.insertCheckItems(listOf(checkItem))

//      세번째 observer 호출
        verify(observer, times(3)).onChanged(captor.capture())

        captor.value[1].checkItems!! shouldContain checkItem
    }

    @Test
    fun `change color theme`() {
        memoRepository.insertMemos(listOf(Memo("1", MemoAttribute(false))))

        memoRepository.searchMemos("").observeForever(observer)
        verify(observer).onChanged(captor.capture())

        Assert.assertThat(captor.value.first().memo.colorTheme, `is`(ColorTheme.Companion.Theme.WHITE.colorTheme))
        val result = captor.value.first()

        result.memo.colorTheme = ColorTheme.Companion.Theme.BLUE.colorTheme
        // 메모 Theme 변경 White -> Blue
        memoRepository.updateMemos(listOf(result.memo))
        // observer 두번째 호출
        verify(observer, times(2)).onChanged(captor.capture())

        Assert.assertThat(captor.value.first().memo.colorTheme, `is`(ColorTheme.Companion.Theme.BLUE.colorTheme))
    }

    @Test
    fun `pin true 정렬 순위`() {
        val expect = Memo("Pin true1", MemoAttribute(true), id = 21)
        val memos = listOf(
                Memo("1", MemoAttribute(false), id = 1),
                expect
        )

        memoRepository.searchMemos("").observeForever(observer)
        memoRepository.insertMemos(memos)

        // Pin true 메모 추가
        val expect2 = Memo("Pin true2", MemoAttribute(true), id = 31)
        memoRepository.insertMemos(listOf(expect2))

        verify(observer, times(3)).onChanged(captor.capture())
        captor.secondValue.first().memo `should equal` expect
        captor.thirdValue.first().memo `should equal` expect2
    }

    @Test
    fun `1 vs N memoview with labels`() {
        val observer = mock<Observer<List<MemoView>>>()
        val captor = argumentCaptor<List<MemoView>>()
        memoRepository.searchMemos("").observeForever(observer)

        memoRepository.insertMemos(listOf(
                Memo("1"),
                Memo("2")
        ))

        val expectLabels = listOf(Label("1", 1), Label("2", 1))
        val labelRepository = LabelRepository(db)

        labelRepository.insertLabels(expectLabels)
        labelRepository.insertLabel(Label("3", 2))

        verify(observer, times(4)).onChanged(captor.capture())

        captor.lastValue.size `should equal` 2
        captor.lastValue.first().labels?.map { it.label } `should equal` listOf("3")
        captor.lastValue[1].labels?.map { it.label } `should equal` listOf("1", "2")
    }
}