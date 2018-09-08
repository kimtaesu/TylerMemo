package com.hucet.tyler.memo.ui.memo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentMemoListBinding
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.utils.AppExecutors
import kotlinx.android.synthetic.main.fragment_memo_list.*
import timber.log.Timber
import javax.inject.Inject


@OpenForTesting
class MemoListFragment : Fragment(), Injectable {

    companion object {
        fun newInstance(): MemoListFragment {
            return MemoListFragment()
        }
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val viewModel: MemoViewModel by lazy {
        viewModelProvider.create(MemoViewModel::class.java)
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    private val adapter: MemoAdapter by lazy { MemoAdapter(appExecutors) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMemoListBinding>(
                inflater,
                R.layout.fragment_memo_list,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        memo_list.apply {
            adapter = this@MemoListFragment.adapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
            }
        }

        viewModel.fetchMemos.observe(this, Observer {
            Timber.d("========== Observer ==========")
            it?.forEach {
                Timber.d("memo: ${it.memo}")
                Timber.d("checkItems: ${it.checkItems}")
            }
            adapter.submitList(it)
        })

        viewModel.showMemos("")
    }
}