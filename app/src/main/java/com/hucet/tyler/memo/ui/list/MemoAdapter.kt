package com.hucet.tyler.memo.ui.list

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.MemoItemBinding
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.vo.Memo
import javax.inject.Inject
import javax.inject.Singleton

class MemoAdapter constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<Memo, MemoItemBinding>(appExecutors, diff) {
    companion object {

        val diff = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo?, newItem: Memo?): Boolean =
                    oldItem?.id == newItem?.id

            override fun areContentsTheSame(oldItem: Memo?, newItem: Memo?): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup): MemoItemBinding {
        return DataBindingUtil.inflate<MemoItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.memo_item,
                parent,
                false
        ).let {
            it
        }
    }

    override fun bind(binding: MemoItemBinding, memo: Memo) {
        binding.memo = memo
    }
}