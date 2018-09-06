package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.UNKNOWN_ID
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.utils.AppExecutors
import kotlinx.android.synthetic.main.fragment_add_memo.*
import timber.log.Timber
import javax.inject.Inject


class AddMemoFragment : Fragment(), Injectable, ColorThemeView {
    companion object {
        fun newInstance(memo: Memo): AddMemoFragment {
            return AddMemoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ArgKeys.KEY_MEMO.name, memo)
                }
            }
        }
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val adapter by lazy {
        LabelAdapter(appExecutors,
                {
                    add_memo_label_list.getChildAdapterPosition(it)
                },
                {
                    (activity as? AddMemoNavigation)?.navigateMakeLabel()
                })
    }

    private val viewModel: AddMemoViewModel by lazy {
        viewModelProvider.create(AddMemoViewModel::class.java)
    }

    private val memo: Memo by lazy {
        arguments?.getParcelable(ArgKeys.KEY_MEMO.name) as? Memo ?: Memo.empty()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddMemoBinding>(
                inflater,
                R.layout.fragment_add_memo,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveMemo(memo)
    }

    private fun initViews() {
        add_memo_text.hint = RandomGreetingHintGenerator.generate()

        viewModel.findMemoViewById(memo.id).observe(this, Observer {
            Timber.d("========== Observer ==========\n" +
                    "labels: ${it}")
            adapter.submitList(it)
        })

        add_memo_label_list.apply {
            adapter = this@AddMemoFragment.adapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
            }
        }
    }

    override fun onColorChanged(colorTheme: ColorTheme) {
        memo.colorTheme = colorTheme
    }
}

