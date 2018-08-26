package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.ui.color.ColorThemeFragment
import com.hucet.tyler.memo.ui.label.MakeLabelViewModel
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_memo.*
import kotlinx.android.synthetic.main.view_add_memo_tools.view.*
import timber.log.Timber
import javax.inject.Inject


class AddMemoFragment : MviFragment<AddMemoView, AddMemoPresenter>(), ManualInjectable, AddMemoView {


    companion object {
        val TOOL_BOX_BACK_STACK_TAG = AddMemoFragment.javaClass.simpleName

        fun newInstance(): AddMemoFragment {
            return AddMemoFragment()
        }
    }

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val viewModel: AddMemoViewModel by lazy {
        viewModelProvider.create(AddMemoViewModel::class.java)
    }

    @Inject
    lateinit var presenter: AddMemoPresenter

    override fun createPresenter(): AddMemoPresenter = presenter

    override fun typingText(): Observable<CharSequence> = RxTextView.textChanges(add_memo_text)

    private val saveMemoSubject = PublishSubject.create<MemoView>()
    override fun saveMemo(): Observable<MemoView> = saveMemoSubject

    //    private var liveData: LiveData<MemoView>? = null
    private var memoId: Long? = null

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

    override fun render(state: AddMemoState) {
        Timber.d("render =============" +
                "memo: ${state.memoView?.memo}\n" +
                "memo_id: ${state.memoView?.memo?.id}\n" +
                "labels: ${state.memoView?.labels}\n" +
                "checklist: ${state.memoView?.checkItems}")
        memoId = state.memoView?.memo?.id
        when {
            !state.isInitSavedMemo -> {
                state.memoView?.run {
                    saveMemoSubject.onNext(this)
                }
            }
//            state.isInitSavedMemo -> {
//                if (liveData == null) {
//                    memoId = state.memoView?.memo?.id
//                    liveData = viewModel.findMemoViewById(state.memoView?.memo?.id!!)
//                    Timber.d("hasObservers ${liveData?.hasActiveObservers()}")
//                    if (liveData?.hasObservers() == false) {
//                        liveData?.observe(this, Observer {
//                            Timber.d("Observer ==========" +
//                                    "memo: ${state.memoView?.memo}\n" +
//                                    "memo_id: ${state.memoView?.memo?.id}\n" +
//                                    "labels: ${state.memoView?.labels}\n" +
//                                    "checklist: ${state.memoView?.checkItems}")
//                        })
//                    }
//                }
//            }
        }
    }

    fun getMemoId() = memoId
}

