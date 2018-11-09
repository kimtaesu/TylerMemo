package com.hucet.tyler.memo.ui.memo

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.databinding.MemoItemBinding
import com.hucet.tyler.memo.utils.AppExecutors

class MemoAdapter constructor(appExecutors: AppExecutors)
    : DataBoundListAdapter<MemoPreviewView, MemoItemBinding>(appExecutors, diff) {
    companion object {

        val diff = object : DiffUtil.ItemCallback<MemoPreviewView>() {
            override fun areItemsTheSame(oldItem: MemoPreviewView?, newItem: MemoPreviewView?): Boolean =
                    oldItem?.memo?.id == newItem?.memo?.id

            override fun areContentsTheSame(oldItem: MemoPreviewView?, newItem: MemoPreviewView?): Boolean =
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

    override fun bind(binding: MemoItemBinding, memoView: MemoPreviewView) {
        binding.memoView = memoView
    }
}