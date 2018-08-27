package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.UNKNOWN_ID
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.Memo
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_memo.*
import timber.log.Timber
import javax.inject.Inject


class AddMemoFragment : Fragment(), Injectable {
    companion object {
        fun newInstance(memoId: Long): AddMemoFragment {
            return AddMemoFragment().apply {
                arguments = Bundle().apply {
                    putLong(ArgKeys.KEY_MEMO_ID.name, memoId)
                }
            }
        }
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val viewModel: AddMemoViewModel by lazy {
        viewModelProvider.create(AddMemoViewModel::class.java)
    }

    private val memoId by lazy { arguments?.getLong(ArgKeys.KEY_MEMO_ID.name) ?: UNKNOWN_ID }

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
        if (memoId == UNKNOWN_ID)
            TODO()

        add_memo_text.hint = RandomGreetingHintGenerator.generate()

        viewModel.findMemoViewById(memoId).observe(this, Observer {
            Timber.d("========== Observer ==========" +
                    "memo: ${it?.memo}\n" +
                    "memo_id: ${it?.memo?.id}\n" +
                    "labels: ${it?.labels}\n" +
                    "checklist: ${it?.checkItems}")
        })
    }
}

