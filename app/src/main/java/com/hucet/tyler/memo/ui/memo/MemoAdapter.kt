package com.hucet.tyler.memo.ui.memo

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.MemoItemBinding
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.utils.AppExecutors

class MemoAdapter constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<MemoView, MemoItemBinding>(appExecutors, diff) {
    companion object {

        val diff = object : DiffUtil.ItemCallback<MemoView>() {
            override fun areItemsTheSame(oldItem: MemoView?, newItem: MemoView?): Boolean =
                    oldItem?.memo?.id == newItem?.memo?.id

            override fun areContentsTheSame(oldItem: MemoView?, newItem: MemoView?): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): MemoItemBinding {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.memo_item,
                parent,
                false
        )
    }

    override fun bind(binding: MemoItemBinding, memoView: MemoView) {
        binding.memo = memoView.memo
        binding.summaryLabels.setLabels(memoView.labels)
    }
}