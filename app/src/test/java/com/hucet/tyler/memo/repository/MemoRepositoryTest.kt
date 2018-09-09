package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.checkitem.CheckItemRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.ui.add.EditMemoView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.shouldEqual
import org.junit.After
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
class MemoRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: MemoRepository

    private lateinit var db: MemoDb


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        val checkItemRepository = CheckItemRepository.CheckItemRepositoryImpl(db.checkItemDao())
        repository = MemoRepository.MemoRepositoryImpl(db.memoDao(), checkItemRepository)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `change color theme`() {
        val observer = mock<Observer<EditMemoView>>()
        val captor = argumentCaptor<EditMemoView>()

        repository.insertMemos(listOf(Memo("1", id = 1)))
        repository.findMemoById(1).observeForever(observer)

        // 메모 Theme 변경 White -> Blue
        repository.updateColorTheme(1, ColorTheme.Companion.Theme.BLUE.colorTheme)
        // observer 두번째 호출
        verify(observer, times(2)).onChanged(captor.capture())

        captor.lastValue.memo.colorTheme shouldEqual ColorTheme.Companion.Theme.BLUE.colorTheme
    }

    @Test
    fun `insert or upate checkitems`() {
        val observer = mock<Observer<EditMemoView>>()
        val captor = argumentCaptor<EditMemoView>()

        val memo = Memo("1", id = 1)
        val checkItems = listOf(
                CheckItem("aaa", false, 1, id = 1),
                CheckItem("bbb", false, 1, id = 2),
                CheckItem("ccc", true, 1, id = 3)
        )
        repository.findMemoById(1).observeForever(observer)
        repository.insertMemo(memo)
        repository.insertCheckItems(checkItems)

//        verify insert
        verify(observer, times(3)).onChanged(captor.capture())
        captor.lastValue.checkItems shouldEqual checkItems

//        verify update
        val updateExpect = CheckItem("cca", false, memoId = 1, id = 3)
        repository.updateCheckItem(updateExpect)
        verify(observer, times(4)).onChanged(captor.capture())
        captor.lastValue.checkItems!!.find { it.name == "cca" }!!.done shouldEqual false
    }
}