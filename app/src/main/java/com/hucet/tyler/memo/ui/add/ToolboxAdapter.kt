package com.hucet.tyler.memo.ui.add

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.CheckItemBinding
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.utils.AppExecutors

private typealias TOOL = CheckItem

class ToolboxAdapter constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<TOOL, CheckItemBinding>(appExecutors, diff) {
    companion object {

        val diff = object : DiffUtil.ItemCallback<TOOL>() {
            override fun areItemsTheSame(oldItem: TOOL?, newItem: TOOL?): Boolean =
                    oldItem?.id == newItem?.id

            override fun areContentsTheSame(oldItem: TOOL?, newItem: TOOL?): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): CheckItemBinding {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.check_item,
                parent,
                false
        )
    }

    override fun bind(binding: CheckItemBinding, item: TOOL) {
        binding.checkItem = item
    }
}