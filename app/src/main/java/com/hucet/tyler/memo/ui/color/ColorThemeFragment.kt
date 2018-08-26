package com.hucet.tyler.memo.ui.color

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.Slide
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentColorThemeBinding
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.ui.add.AddMemoFragment.Companion.TOOL_BOX_BACK_STACK_TAG
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.ColorTheme
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.color_theme_item.view.*
import kotlinx.android.synthetic.main.fragment_color_theme.*
import java.lang.ref.WeakReference
import javax.inject.Inject

typealias ColorChangedListener = (ColorTheme) -> Unit

class ColorThemeFragment : MviFragment<ColorThemeView, ColorLabelPresenter>(), ManualInjectable, ColorThemeView {
    companion object {
        fun newInstance(listener: ColorChangedListener): ColorThemeFragment {
            return ColorThemeFragment().apply {
                colorChangedListener = listener
            }
        }
    }

    private var colorChangedListener: ColorChangedListener? = null


    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var presenter: ColorLabelPresenter


    private val adapter: ColorThemeAdapter by lazy {
        ColorThemeAdapter(appExecutors, { v: View ->
            v.color_view
            color_list.getChildAdapterPosition(v)
        }, {
            colorChangedListener?.invoke(it)
        })
    }

    override fun createPresenter(): ColorLabelPresenter = presenter

    private val createLabelPublishSubject = PublishSubject.create<ColorTheme>()
    private val changedColorPublishSubject = PublishSubject.create<ColorTheme>()

    override fun createdColor(): Observable<ColorTheme> = createLabelPublishSubject
    override fun changedColor(): Observable<ColorTheme> = changedColorPublishSubject

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
        close.setOnClickListener {
            activity?.run {
                supportFragmentManager.popBackStack(TOOL_BOX_BACK_STACK_TAG, POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    override fun render(state: ColorThemeState) {
        adapter.submitList(state.colorThemes)
    }
}