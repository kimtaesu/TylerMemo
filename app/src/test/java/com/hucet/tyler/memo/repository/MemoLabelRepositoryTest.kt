package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.utils.TestUtils
import com.hucet.tyler.memo.vo.CheckableLabelView
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.mock
import org.amshove.kluent.shouldEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
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

    @Captor
    private lateinit var labelCaptor: ArgumentCaptor<List<Label>>

    @Captor
    private lateinit var memoCaptor: ArgumentCaptor<List<Memo>>

    @Captor
    private lateinit var checkLabelCaptor: ArgumentCaptor<List<CheckableLabelView>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = MemoLabelRepository(db)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `CheckableLabelView checked true associate with memo id`() {
        val chechedMemoId = 1L
        val observer = mock<Observer<List<CheckableLabelView>>>()
        TestUtils.generateMemoLabel(db, 2)
        reset(observer)

        repository.searchCheckedLabels(chechedMemoId).observeForever(observer)
        repository.insertMemoLabelJoin(MemoLabelJoin(chechedMemoId, 1))

        verify(observer, times(2)).onChanged(checkLabelCaptor.capture())

        checkLabelCaptor.value.size shouldEqual 2
        checkLabelCaptor.value[0].isChecked shouldEqual true
        checkLabelCaptor.value[1].isChecked shouldEqual false


        val labelId = LabelRepository(db).insertLabel(Label("label_3", 1))
        verify(observer, times(3)).onChanged(checkLabelCaptor.capture())

        labelId?.run {
            repository.insertMemoLabelJoin(MemoLabelJoin(chechedMemoId, labelId))
        }

        verify(observer, times(4)).onChanged(checkLabelCaptor.capture())

        checkLabelCaptor.value.size shouldEqual 3
        checkLabelCaptor.value[0].isChecked shouldEqual true
        checkLabelCaptor.value[1].isChecked shouldEqual false
        checkLabelCaptor.value[2].isChecked shouldEqual true
    }

    @Test
    fun `get labels by memo id`() {
        val observer = mock<Observer<List<Label>>>()
        TestUtils.generateMemoLabel(db)
        reset(observer)


        repository.getLabelByMemo(1).observeForever(observer)

        repository.insertMemoLabelJoin(MemoLabelJoin(1, 1))
        repository.insertMemoLabelJoin(MemoLabelJoin(1, 2))

        repository.insertMemoLabelJoin(MemoLabelJoin(2, 2))
        repository.insertMemoLabelJoin(MemoLabelJoin(3, 2))

        verify(observer, times(5)).onChanged(labelCaptor.capture())

        labelCaptor.value.size shouldEqual 2
        labelCaptor.value[0].id shouldEqual 1
        labelCaptor.value[1].id shouldEqual 2
    }

    @Test
    fun `get memos by label id`() {
        val observer = mock<Observer<List<Memo>>>()
        TestUtils.generateMemoLabel(db)
        reset(observer)


        repository.getMemoByLabel(1).observeForever(observer)

        repository.insertMemoLabelJoin(MemoLabelJoin(1, 1))
        repository.insertMemoLabelJoin(MemoLabelJoin(1, 2))

        repository.insertMemoLabelJoin(MemoLabelJoin(2, 2))
        repository.insertMemoLabelJoin(MemoLabelJoin(3, 1))

        verify(observer, times(5)).onChanged(memoCaptor.capture())

        memoCaptor.value.size shouldEqual 2
        memoCaptor.value[0].id shouldEqual 1
        memoCaptor.value[1].id shouldEqual 3
    }
}