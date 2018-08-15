package com.hucet.tyler.memo.ui.label

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.SpacesItemDecoration
import com.hucet.tyler.memo.databinding.FragmentColorLabelBinding
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.utils.Util
import com.hucet.tyler.memo.vo.Label
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_color_label.*
import javax.inject.Inject


class ColorLabelFragment : MviFragment<ColorLabelView, ColorLabelPresenter>(), Injectable, ColorLabelView {
    companion object {
        fun newInstance(): ColorLabelFragment {
            return ColorLabelFragment()
        }
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var presenter: ColorLabelPresenter

    private val adapter: LabelAdapter by lazy { LabelAdapter(appExecutors) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentColorLabelBinding>(
                inflater,
                R.layout.fragment_color_label,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        label_list.apply {
            adapter = this@ColorLabelFragment.adapter
            addItemDecoration(SpacesItemDecoration(Util.dpToPx(context, 5.0f)))
        }
    }

    override fun createPresenter(): ColorLabelPresenter = presenter

    private val createLabelPublishSubject = PublishSubject.create<Label>()

    override fun createdLabel(): Observable<Label> = createLabelPublishSubject

    override fun render(state: ColorLabelState) {
        if (state.isNeedUpdate) {
            adapter.submitList(state.labels)
        }
    }
}