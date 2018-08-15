package com.hucet.tyler.memo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.di.Injectable
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MemoListFragment : MviFragment<MemoListView, MemoListPresenter>() {

    companion object {
        fun newInstance(): MemoListFragment {
            return MemoListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_memo_list, container, false)

    @Inject
    lateinit var presenter: MemoListPresenter

    override fun createPresenter(): MemoListPresenter = presenter
}