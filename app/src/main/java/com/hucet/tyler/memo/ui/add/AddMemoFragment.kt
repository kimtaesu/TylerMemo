package com.hucet.tyler.memo.ui.add

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.vo.Memo
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_memo.*
import timber.log.Timber
import javax.inject.Inject

class AddMemoFragment : MviFragment<AddMemoView, AddMemoPresenter>(), ManualInjectable, AddMemoView {

    companion object {
        fun newInstance(): AddMemoFragment {
            return AddMemoFragment()
        }
    }

    @Inject
    lateinit var presenter: AddMemoPresenter

    override fun createPresenter(): AddMemoPresenter = presenter

    private val createMemoSubject = PublishSubject.create<Memo>()
    override fun createMemo(): Observable<Memo> = createMemoSubject

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

        button.setOnClickListener {
            val newMemo = Memo("", add_memo_text.text.toString())
            createMemoSubject.onNext(newMemo)
        }
    }

    override fun render(state: AddMemoState) {
        Timber.d("state ${state}")
        when {
            state.createdMemo -> {
                activity?.finish()
            }
        }
    }
}