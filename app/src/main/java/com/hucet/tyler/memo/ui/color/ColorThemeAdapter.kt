package com.hucet.tyler.memo.ui.color

import android.databinding.DataBindingUtil
import android.graphics.PorterDuff
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.tyler.memo.ListClickListener
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.ColorThemeItemBinding
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.db.model.ColorTheme


class ColorThemeAdapter constructor(appExecutors: AppExecutors,
                                    private val getItemPosition: (View) -> Int,
                                    private val onClickListener: ListClickListener<ColorTheme>)
    : DataBoundListAdapter<ColorTheme, ColorThemeItemBinding>(appExecutors, diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<ColorTheme>() {
            override fun areItemsTheSame(oldItem: ColorTheme?, newItem: ColorTheme?): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: ColorTheme?, newItem: ColorTheme?): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ColorThemeItemBinding {
        val binding: ColorThemeItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.color_theme_item,
                parent,
                false
        )
        binding.root.setOnClickListener {
            val position = getItemPosition(it)
            onClickListener.invoke(getItem(position))
        }
        return binding
    }

    override fun bind(binding: ColorThemeItemBinding, item: ColorTheme) {
        binding.colorView.background.setColorFilter(item.color, PorterDuff.Mode.DARKEN)
    }
}