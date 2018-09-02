package com.hucet.tyler.memo.ui

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.LabelItemsBinding
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.utils.AppExecutors


class LabelAdapter constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<Label, LabelItemsBinding>(appExecutors, diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<Label>() {
            override fun areItemsTheSame(oldItem: Label, newItem: Label): Boolean =
                    oldItem?.id == newItem?.id

            override fun areContentsTheSame(oldItem: Label, newItem: Label): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): LabelItemsBinding {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.label_items,
                parent,
                false
        )
    }

    override fun bind(binding: LabelItemsBinding, label: Label) {
        binding.label = label
    }
}