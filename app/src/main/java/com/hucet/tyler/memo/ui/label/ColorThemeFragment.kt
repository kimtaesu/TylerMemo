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
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_color_label.*
import javax.inject.Inject


class ColorThemeFragment : MviFragment<ColorThemeView, ColorLabelPresenter>(), Injectable, ColorThemeView {
    companion object {
        fun newInstance(): ColorThemeFragment {
            return ColorThemeFragment()
        }
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var presenter: ColorLabelPresenter

    private val adapter: ColorThemeAdapter by lazy { ColorThemeAdapter(appExecutors) }

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
            adapter = this@ColorThemeFragment.adapter
            addItemDecoration(SpacesItemDecoration(Util.dpToPx(context, 5.0f)))
        }
    }

    override fun createPresenter(): ColorLabelPresenter = presenter

    private val createLabelPublishSubject = PublishSubject.create<ColorTheme>()

    override fun createdLabel(): Observable<ColorTheme> = createLabelPublishSubject

    override fun render(state: ColorThemeState) {
        adapter.submitList(state.colorThemes)
    }
}