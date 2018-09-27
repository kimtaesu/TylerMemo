package com.hucet.tyler.memo.ui.color

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentColorThemeBinding
import com.hucet.tyler.memo.di.Injectable
import com.hucet.tyler.memo.ui.add.ColorThemeView
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.ui.add.AddMemoActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_color_theme.*
import javax.inject.Inject

class ColorThemeFragment : Fragment(), Injectable {
    companion object {
        fun newInstance(): ColorThemeFragment {
            return ColorThemeFragment().apply {
            }
        }
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    private val adapter: ColorThemeAdapter by lazy {
        ColorThemeAdapter(appExecutors, { v: View ->
            color_list.getChildAdapterPosition(v)
        }, {
            (activity as? ColorThemeView)?.onColorChanged(it)
        })
    }

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
            activity?.supportFragmentManager?.popBackStack(AddMemoActivity.TOOL_BOX_BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        adapter.submitList(ColorTheme.initialPopulate())
    }
}