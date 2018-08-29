package com.hucet.tyler.memo.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.vo.CheckableLabelView
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
class LabelRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: LabelRepository

    private lateinit var db: MemoDb

    @Mock
    private lateinit var observer: Observer<List<CheckableLabelView>>
    @Captor
    private lateinit var captor: ArgumentCaptor<List<CheckableLabelView>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = LabelRepository(db)
        reset(observer)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `CheckableLabelView checked true`() {
        val memoRepository = MemoRepository(db).apply {
            for (i in 0 until 5) {
                insertMemo(Memo("${i}"))
            }
        }

        val memo = Memo("label checked")
        val id = memoRepository.insertMemo(memo)

        repository.searchCheckedLabels("").observeForever(observer)

        repository.insertLabel(Label("1", id))
        repository.insertLabel(Label("2", id))
        repository.insertLabel(Label("3", null))

        verify(observer, times(4)).onChanged(captor.capture())

        captor.value.find { it.label == "1" }!!.isChecked `should equal` true
        captor.value.find { it.label == "2" }!!.isChecked `should equal` true
        captor.value.find { it.label == "3" }!!.isChecked `should equal` false

    }
}
