package com.hucet.tyler.memo.ui.color

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentColorThemeBinding
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.ColorTheme
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_color_theme.*
import javax.inject.Inject


class ColorThemeFragment : MviFragment<ColorThemeView, ColorLabelPresenter>(), ManualInjectable, ColorThemeView {
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
        val binding = DataBindingUtil.inflate<FragmentColorThemeBinding>(
                inflater,
                R.layout.fragment_color_theme,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        color_list.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            adapter = this@ColorThemeFragment.adapter
        }
    }

    override fun createPresenter(): ColorLabelPresenter = presenter

    private val createLabelPublishSubject = PublishSubject.create<ColorTheme>()

    override fun createdColor(): Observable<ColorTheme> = createLabelPublishSubject

    override fun render(state: ColorThemeState) {
        adapter.submitList(state.colorThemes)
    }
}