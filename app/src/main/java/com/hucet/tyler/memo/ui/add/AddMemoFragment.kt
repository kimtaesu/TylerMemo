package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
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

    private val saveMemoSubject = PublishSubject.create<Memo>()
    override fun saveMemo(): Observable<Memo> = saveMemoSubject

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
                "memo: ${state.memo}\n" +
                "memo_id: ${state.memo?.id}\n"
        )
        memoId = state.memo?.id
        when {
            !state.isInitSavedMemo -> {
                state.memo?.run {
                    saveMemoSubject.onNext(this)
                }
            }
//            state.isInitSavedMemo -> {
//                if (liveData == null) {
//                    memoId = state.memo?.memo?.id
//                    liveData = viewModel.findMemoViewById(state.memo?.memo?.id!!)
//                    Timber.d("hasObservers ${liveData?.hasActiveObservers()}")
//                    if (liveData?.hasObservers() == false) {
//                        liveData?.observe(this, Observer {
//                            Timber.d("Observer ==========" +
//                                    "memo: ${state.memo?.memo}\n" +
//                                    "memo_id: ${state.memo?.memo?.id}\n" +
//                                    "labels: ${state.memo?.labels}\n" +
//                                    "checklist: ${state.memo?.checkItems}")
//                        })
//                    }
//                }
//            }
        }
    }

    fun getMemoId() = memoId
}

