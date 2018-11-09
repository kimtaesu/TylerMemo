package com.hucet.tyler.memo.repository.colortheme

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.checkitem.CheckItemRepository
import com.hucet.tyler.memo.repository.label.LabelRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.ui.label.CheckableLabelView
import com.hucet.tyler.memo.util.rx.RxImmediateSchedulerRule
import com.hucet.tyler.memo.utils.TestUtils
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.After
import org.junit.Assert.*
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
class ColorThemeRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var repository: ColorThemeRepository

    private lateinit var db: MemoDb

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        db = MemoDb.getInstanceInMemory(RuntimeEnvironment.application)
        repository = ColorThemeRepository.ColorThemeRepositoryImpl(db.colorThemeDao())
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `The application must have initial data the first time it is loaded`() {
        val observer = mock<Observer<List<ColorTheme>>>()
        val captor = argumentCaptor<List<ColorTheme>>()

        repository.getAllColorThemes().observeForever(observer)

        verify(observer, times(2)).onChanged(captor.capture())
        captor.lastValue.excludeId() `should equal` ColorTheme.initialPopulate()
    }

    @Test
    fun `Look for ColorTheme as a Memo id in a 1 to 1 relationship`() {
        val observer = mock<Observer<ColorTheme>>()
        val captor = argumentCaptor<ColorTheme>()

        val memoRepository = MemoRepository.MemoRepositoryImpl(db.memoDao(), CheckItemRepository.CheckItemRepositoryImpl(db.checkItemDao()))
        val firstMemoId = memoRepository.insertMemo(Memo("first"))

        repository.getColorThemeByMemoId(firstMemoId!!).observeForever(observer)

        verify(observer, times(1)).onChanged(captor.capture())
        captor.lastValue.id `should equal` ColorTheme.defaultId

        val expectColorThemeId = ColorTheme.Companion.Theme.CYAN.colorTheme.id
        val secondMemoId = memoRepository.insertMemo(Memo("second", colorThemeId = expectColorThemeId))
        repository.getColorThemeByMemoId(secondMemoId!!).observeForever(observer)
        verify(observer, times(2)).onChanged(captor.capture())
        captor.lastValue.id `should equal` expectColorThemeId
    }
}

private fun <E : ColorTheme> List<E>.excludeId(): List<ColorTheme> {
    return this.map { it.copy(id = 0) }
}
