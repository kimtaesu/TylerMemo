package com.hucet.tyler.memo.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentMemoListBinding
import com.hucet.tyler.memo.vo.Memo
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_memo_list.*
import javax.inject.Inject

class MemoListFragment : MviFragment<MemoListView, MemoListPresenter>(), MemoListView {
    companion object {
        fun newInstance(): MemoListFragment {
            return MemoListFragment()
        }
    }

    @Inject
    lateinit var presenter: MemoListPresenter

    @Inject
    lateinit var adapter: MemoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMemoListBinding>(
                inflater,
                R.layout.fragment_memo_list,
                container,
                false
        )
        return binding.root
    }


    override fun createPresenter(): MemoListPresenter = presenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        list.apply {
            adapter = this@MemoListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        val i = listOf(
                Memo("tt666t", "asㅗㅗdad"),
                Memo( "ttt1", "asdad3"),
                Memo( "ttt2", "asdad2")
        )
        adapter.submitList(i)
    }

    override fun render(state: MemoState) {
        when {
            state.memo == null -> {
                fetchMemos()
            }
        }
    }

    private fun fetchMemos() {
    }

}