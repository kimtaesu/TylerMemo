package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.*
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.nhaarman.mockito_kotlin.*
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
//        repository = MemoRepository(db)
        reset(observer)
    }

    @After
    fun closeDb() {
        db.close()
    }

//
//    @Test
//    fun `ko search text memo`() {
//        val expect = Memo("하하", id = 3)
//        repository.searchMemos("하").observeForever(observer)
//        repository.insertMemos(listOf(expect, Memo("ㅁㄴㅇㄴㅇ", id = 1)))
//
//        verify(observer, times(2)).onChanged(captor.capture())
//
//        captor.value.size shouldEqual 1
//        captor.value.first() shouldEqual expect
//    }
//
//    @Test
//    fun `insert integration obsever`() {
//        val memos = listOf(
//                Memo("1", MemoAttribute(false), id = 1),
//                Memo("2", MemoAttribute(false), id = 2)
//        )
//
//        val checkItem = CheckItem("ddd", true, 2, 1)
//
//        // search memo observer 등록
//        repository.searchMemos("").observeForever(observer)
//        verify(observer).onChanged(captor.capture())
//
////        Insert a memo
//        repository.insertMemos(memos)
////      두번째 observer 호출
//        verify(observer, times(2)).onChanged(captor.capture())
//
//        captor.value shouldContainAll memos
//
////        Insert a check item
//        repository.insertCheckItems(listOf(checkItem))
//
////      세번째 observer 호출
//        verify(observer, times(3)).onChanged(captor.capture())
//
////        captor.value[1].checkItems!! shouldContain checkItem
//    }
//
//    @Test
//    fun `change color theme`() {
//        repository.insertMemos(listOf(Memo("1", MemoAttribute(false))))
//
//        repository.searchMemos("").observeForever(observer)
//        verify(observer).onChanged(captor.capture())
//
//        Assert.assertThat(captor.value.first().colorTheme, `is`(ColorTheme.Companion.Theme.WHITE.colorTheme))
//        val result = captor.value.first()
//
//        result.colorTheme = ColorTheme.Companion.Theme.BLUE.colorTheme
//        // 메모 Theme 변경 White -> Blue
//        repository.updateColorTheme(listOf(result))
//        // observer 두번째 호출
//        verify(observer, times(2)).onChanged(captor.capture())
//
//        Assert.assertThat(captor.value.first().colorTheme, `is`(ColorTheme.Companion.Theme.BLUE.colorTheme))
//    }

    @Test
    fun `update checkitems`() {
        repository.insertMemos(listOf(Memo("1", id = 1), Memo("2", id = 2)))
        repository.
    }
}