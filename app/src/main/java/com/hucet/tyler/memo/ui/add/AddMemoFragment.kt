package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DaggerMviFragment
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.utils.AppExecutors
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_memo.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class AddMemoFragment : DaggerMviFragment<AddMemoView, AddMemoPresenter>(), ColorThemeView, AddMemoView {

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

    @Inject
    lateinit var presenter: AddMemoPresenter

    lateinit var binding: FragmentAddMemoBinding

    private val checkAdapter by lazy {
        CheckItemAdapter(appExecutors)
    }
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
        binding = DataBindingUtil.inflate(
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
        saveMemoEmit.onNext(Unit)
    }

    private fun initViews() {
        add_memo_text.hint = RandomGreetingHintGenerator.generate()
        add_memo_check_list.apply {
            adapter = checkAdapter
            layoutManager = LinearLayoutManager(context)
        }

        add_memo_label_list.apply {
            adapter = this@AddMemoFragment.adapter
            layoutManager = FlexboxLayoutManager(context).apply {
                flexWrap = FlexWrap.WRAP
            }
        }

        viewModel.getEditMemoById(memo.id).observe(this, Observer {
            Timber.d("========== Observer ==========\n" +
                    "edit memo: $it")
            it?.run {
                fetchEditMemoEmit.onNext(this)
                colorThemeChangedEmit.onNext(this.memo.colorThemeId)
            }
        })

        viewModel.getLabelByMemo(memo.id).observe(this, Observer {
            Timber.d("========== Observer ==========\n" +
                    "labels: $it")
            adapter.submitList(it)
        })
    }

    fun onAddCheckItem() = createCheckItemEmit.onNext(CheckItem.empty(memo.id))
    fun onClickedCheckItems(isShown: Boolean) = viewCheckItemsEmit.onNext(isShown)

    override fun onColorChanged(colorTheme: ColorTheme) = colorThemeChangedEmit.onNext(colorTheme.id)

    override fun render(state: AddMemoState) {
        when {
            state.isShowCheckItems -> {
                val items = if (state.editMemoView?.checkItems?.isEmpty() == true)
                    listOf(CheckItem.empty(memo.id))
                else
                    state.editMemoView?.checkItems

                checkAdapter.submitList(items)
            }
            state.editMemoView != null -> {
                binding.memo = state.editMemoView.memo
            }
        }
    }

    /**
     * Mvi pattern
     */
    override fun createPresenter(): AddMemoPresenter = presenter

    private val colorThemeChangedEmit = PublishSubject.create<Long>()
    private val saveMemoEmit = PublishSubject.create<Any>()
    private val createCheckItemEmit = PublishSubject.create<CheckItem>()
    private val viewCheckItemsEmit = PublishSubject.create<Boolean>()
    private val fetchEditMemoEmit = PublishSubject.create<EditMemoView>()

    override fun saveMemo(): Observable<Any> = saveMemoEmit

    override fun viewCheckItems(): Observable<Boolean> = viewCheckItemsEmit

    override fun createCheckItem(): Observable<CheckItem> = createCheckItemEmit

    override fun fetchEditMemo(): Observable<EditMemoView> = fetchEditMemoEmit

    override fun typingText(): Observable<CharSequence> = RxTextView.textChanges(add_memo_text).throttleLast(500, TimeUnit.MILLISECONDS)

    override fun colorThemeChanged(): Observable<Long> = colorThemeChangedEmit
}