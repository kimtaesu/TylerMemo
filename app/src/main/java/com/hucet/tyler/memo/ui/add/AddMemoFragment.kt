package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.vo.Memo
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_memo.*
import timber.log.Timber
import javax.inject.Inject


class AddMemoFragment : MviFragment<AddMemoView, AddMemoPresenter>(), ManualInjectable {


    companion object {
        fun newInstance(memo: Memo): AddMemoFragment {
            return AddMemoFragment().apply {
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

    private val viewModel: AddMemoViewModel by lazy {
        viewModelProvider.create(AddMemoViewModel::class.java)
    }

    @Inject
    lateinit var presenter: AddMemoPresenter

    override fun createPresenter(): AddMemoPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
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

    private fun initViews() {
        add_memo_text.hint = RandomGreetingHintGenerator.generate()
    }
}

