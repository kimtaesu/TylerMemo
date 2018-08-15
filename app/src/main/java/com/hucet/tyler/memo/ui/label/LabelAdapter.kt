package com.hucet.tyler.memo.ui.label

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.LabelItemBinding
import com.hucet.tyler.memo.databinding.MemoItemBinding
import com.hucet.tyler.memo.ui.list.MemoAdapter
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import javax.inject.Inject
import javax.inject.Singleton

class LabelAdapter constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<Label, LabelItemBinding>(appExecutors, diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<Label>() {
            override fun areItemsTheSame(oldItem: Label?, newItem: Label?): Boolean =
                    oldItem?.id == newItem?.id

            override fun areContentsTheSame(oldItem: Label?, newItem: Label?): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup): LabelItemBinding {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.label_item,
                parent,
                false
        )
    }

    override fun bind(binding: LabelItemBinding, item: Label) {
        binding.colorLabel.setBackgroundColor(item.color)
    }
}