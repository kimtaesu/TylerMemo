package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.*
import com.hucet.tyler.memo.repository.checkitem.CheckItemRepository
import com.hucet.tyler.memo.ui.memo.MemoPreviewView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.utils.TestUtils
import com.hucet.tyler.memo.ui.label.CheckableLabelView
import com.hucet.tyler.memo.repository.label.LabelRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.repository.memolabel.MemoLabelRepository
import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
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
class MemoLabelRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: MemoLabelRepository

    private lateinit var db: MemoDb

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = MemoLabelRepository(
                db.memoLabelJoinDao(),
                LabelRepository.LabelRepositoryImpl(db.labelDao()),
                MemoRepository.MemoRepositoryImpl(db.memoDao(), CheckItemRepository.CheckItemRepositoryImpl(db.checkItemDao()))
        )
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `CheckableLabelView checked true associate with memo id`() {
        val checkedMemoId = 1L
        val observer = mock<Observer<List<CheckableLabelView>>>()
        val captor = argumentCaptor<List<CheckableLabelView>>()
        TestUtils.generateMemoLabel(db, 2)

        repository.searchCheckedLabels(checkedMemoId).observeForever(observer)
        repository.insertMemoLabelJoin(MemoLabelJoin(checkedMemoId, 1))


        repository.insertLabel(Label("label_3", id = 3))
        repository.insertMemoLabelJoin(MemoLabelJoin(checkedMemoId, 3))

        verify(observer, times(4)).onChanged(captor.capture())

        captor.lastValue.size shouldEqual 3
        captor.lastValue[0].isChecked shouldEqual true
        captor.lastValue[1].isChecked shouldEqual false
        captor.lastValue[2].isChecked shouldEqual true
    }

    @Test
    fun `get labels by memo id`() {
        val observer = mock<Observer<List<Label>>>()
        val captor = argumentCaptor<List<Label>>()
        TestUtils.generateMemoLabel(db)
        reset(observer)


        repository.getLabelByMemo(1).observeForever(observer)

        repository.insertMemoLabelJoin(MemoLabelJoin(1, 1))
        repository.insertMemoLabelJoin(MemoLabelJoin(1, 2))

        repository.insertMemoLabelJoin(MemoLabelJoin(2, 2))
        repository.insertMemoLabelJoin(MemoLabelJoin(3, 2))

        verify(observer, times(5)).onChanged(captor.capture())

        captor.lastValue.size shouldEqual 2
        captor.lastValue[0].id shouldEqual 1
        captor.lastValue[1].id shouldEqual 2
    }

    @Test
    fun `get memos by label id`() {
        val observer = mock<Observer<List<Memo>>>()
        val captor = argumentCaptor<List<Memo>>()
        TestUtils.generateMemoLabel(db)
        reset(observer)


        repository.getMemoByLabel(1).observeForever(observer)

        repository.insertMemoLabelJoin(MemoLabelJoin(1, 1))
        repository.insertMemoLabelJoin(MemoLabelJoin(1, 2))

        repository.insertMemoLabelJoin(MemoLabelJoin(2, 2))
        repository.insertMemoLabelJoin(MemoLabelJoin(3, 1))

        verify(observer, times(5)).onChanged(captor.capture())

        captor.lastValue.size shouldEqual 2
        captor.lastValue[0].id shouldEqual 1
        captor.lastValue[1].id shouldEqual 3
    }

    @Test
    fun `search memo with labels`() {
        val observer = mock<Observer<List<MemoPreviewView>>>()
        val captor = argumentCaptor<List<MemoPreviewView>>()
        val (memos, labels) = TestUtils.generateMemoLabel(db, 3)
        reset(observer)

        repository.searchMemoView(memos[0].text).observeForever(observer)
        verify(observer, times(1)).onChanged(captor.capture())

        captor.lastValue.size shouldEqual 1
        captor.lastValue[0].memo shouldEqual memos[0]
    }

    @Test
    fun `memo with labels`() {
        val observer = mock<Observer<List<MemoPreviewView>>>()
        val captor = argumentCaptor<List<MemoPreviewView>>()

        val (memos, labels) = TestUtils.generateMemoLabel(db, 3)
        reset(observer)

        repository.searchMemoView("", false).observeForever(observer)

        repository.insertMemoLabelJoin(MemoLabelJoin(1, 1))
        repository.insertMemoLabelJoin(MemoLabelJoin(1, 2))



        repository.insertMemoLabelJoin(MemoLabelJoin(2, 3))
        verify(observer, times(4)).onChanged(captor.capture())

        captor.lastValue.size shouldEqual 3

        captor.lastValue.find { it.memo.id == 1L }!!.displayLabels shouldEqual labels.filterId(arrayOf(1L, 2L))
        captor.lastValue.find { it.memo.id == 2L }!!.displayLabels shouldEqual labels.filterId(arrayOf(3L))
        captor.lastValue.find { it.memo.id == 3L }!!.displayLabels shouldEqual emptyList()
    }

    @Test
    fun `pin true 정렬 순위`() {
        val observer = mock<Observer<List<MemoPreviewView>>>()
        val captor = argumentCaptor<List<MemoPreviewView>>()

        val expect = Memo("Pin true1", MemoAttribute(true), id = 21)
        val memos = listOf(Memo("1", MemoAttribute(false), id = 1), expect)

        repository.searchMemoView("", true).observeForever(observer)
        repository.insertMemos(memos)

        // Pin true 메모 추가
        val expect2 = Memo("Pin true2", MemoAttribute(true), id = 31)
        repository.insertMemos(listOf(expect2))

        verify(observer, times(3)).onChanged(captor.capture())
        captor.secondValue.first().memo `should equal` expect
        captor.thirdValue.first().memo `should equal` expect2
    }
}

private fun <E : HasId> List<E>.filterId(ids: Array<Long>): List<E> {
    return this.filter {
        it.id in ids
    }
}
