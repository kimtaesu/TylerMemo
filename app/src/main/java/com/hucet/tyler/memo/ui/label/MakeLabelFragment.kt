package com.hucet.tyler.memo.ui.label

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentMakeLabelBinding
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.Memo
import kotlinx.android.synthetic.main.fragment_make_label.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MakeLabelFragment : Fragment(), Injectable {
    companion object {
        fun newInstance(memo: Memo): MakeLabelFragment {
            return MakeLabelFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ArgKeys.KEY_MEMO.name, memo)
                }
            }
        }
    }

    private val memo by lazy {
        arguments?.getParcelable(ArgKeys.KEY_MEMO.name) as Memo
    }
    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory


    private val viewModel: MakeLabelViewModel by lazy {
        viewModelProvider.create(MakeLabelViewModel::class.java)
    }
    @Inject
    lateinit var appExecutors: AppExecutors

    private val adapter by lazy {
        MakeLabelAdapter(
                appExecutors,
                {
                    label_list.getChildAdapterPosition(it)
                },
                {
                },
                {
                    viewModel.createLabel(it, memo.id)
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMakeLabelBinding>(
                inflater,
                R.layout.fragment_make_label,
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
        label_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MakeLabelFragment.adapter
        }
        (activity as? SearchView)?.run {
            viewModel.bindSearch(this.searchView().throttleLast(500, TimeUnit.MILLISECONDS))
        }

        viewModel.fetchLabels.observe(this, Observer {
            val keyword = it?.first ?: ""
            val items = it?.second ?: emptyList()
            if (items.isEmpty() && !keyword.isEmpty())
                adapter.setMakeLabelVisible(keyword)
            else
                adapter.setMakeLabelVisible(null)

            adapter.submitList(items)
        })
    }
}
