package com.hucet.tyler.memo.ui.add

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.tyler.memo.ListClickListener
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.LabelItemsBinding
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.utils.AppExecutors

private typealias ITEM = Label

class LabelAdapter constructor(appExecutors: AppExecutors,
                               private val getItemPosition: (View) -> Int,
                               private val onClickListener: ListClickListener<ITEM>)
    : DataBoundListAdapter<ITEM, LabelItemsBinding>(appExecutors, diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<ITEM>() {
            override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM): Boolean =
                    oldItem?.id == newItem?.id

            override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): LabelItemsBinding {
        return DataBindingUtil.inflate<LabelItemsBinding>(
                LayoutInflater.from(parent.context),
                R.layout.label_items,
                parent,
                false
        ).apply {
            this.root.setOnClickListener {
                val position = getItemPosition(it)
                onClickListener(getItem(position))
            }
        }
    }

    override fun bind(binding: LabelItemsBinding, label: ITEM) {
        binding.label = label
    }
}