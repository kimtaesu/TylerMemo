package com.hucet.tyler.memo.list

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.common.DataBoundViewHolder
import com.hucet.tyler.memo.databinding.MemoItemBinding
import com.hucet.tyler.memo.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoAdapter @Inject constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<Memo, MemoItemBinding>(appExecutors, diff) {
    companion object {

        val diff = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo?, newItem: Memo?): Boolean =
                    oldItem?.subject == newItem?.subject

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

    override fun bind(binding: MemoItemBinding, item: Memo) {
        binding.subject.text = item.subject
        binding.text.text = item.text
    }
}