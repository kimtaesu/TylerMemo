package com.hucet.tyler.memo.ui.list

import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.SingleFragmentActivity
import com.hucet.tyler.memo.util.TestUtil
import com.hucet.tyler.memo.utils.*
import com.hucet.tyler.memo.vo.Memo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MemoListFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, false, true)
    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()
    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()
    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private lateinit var viewModel: MemoViewModel
    private lateinit var memoFragment: MemoListFragment

    private lateinit var MemosLiveData: MutableLiveData<List<Memo>>
    @Before
    fun init() {
        MemosLiveData = MutableLiveData()
        viewModel = mock {
            on { fetchMemos } doReturn MemosLiveData
        }
        memoFragment = MemoListFragment.newInstance()
        memoFragment.viewModelProvider = ViewModelUtil.createFor(viewModel)
        memoFragment.appExecutors = countingAppExecutors.appExecutors
        activityRule.activity.setFragment(memoFragment)
        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }

    @Test
    fun memo_load() {
        val memo = TestUtil.createMemo("foo", "bar")
        MemosLiveData.postValue(listOf(memo))
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("foo"))))
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText("bar"))))
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.memo_list)
    }

}