package com.hucet.tyler.memo.ui.label

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.DataBoundListAdapter
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.ListClickListener
import com.hucet.tyler.memo.common.DataBoundViewHolder
import com.hucet.tyler.memo.databinding.MakeLabelExistItemBinding
import com.hucet.tyler.memo.databinding.MakeLabelNewItemBinding
import timber.log.Timber

private typealias ITEM = CheckableLabelView

class MakeLabelAdapter constructor(appExecutors: AppExecutors,
                                   private val getItemPosition: (View) -> Int,
                                   private val onClickListener: ListClickListener<ITEM>,
                                   private val onNewLabelClickListener: ListClickListener<String>)
    : DataBoundListAdapter<ITEM, ViewDataBinding>(appExecutors, diff) {

    private var makeLabelKeyword: String? = null

    companion object {
        val diff = object : DiffUtil.ItemCallback<ITEM>() {
            override fun areItemsTheSame(oldItem: ITEM?, newItem: ITEM?): Boolean =
                    oldItem?.label_id == newItem?.label_id

            override fun areContentsTheSame(oldItem: ITEM?, newItem: ITEM?): Boolean =
                    oldItem == newItem
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return when (viewType) {
            R.layout.make_label_new_item -> {
                DataBindingUtil.inflate<MakeLabelNewItemBinding>(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                ).apply {
                    root.setOnClickListener {
                        makeLabelKeyword?.run(onNewLabelClickListener)
                    }
                }
            }
            else -> {
                DataBindingUtil.inflate<MakeLabelExistItemBinding>(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                ).apply {
                    root.setOnClickListener {
                        val position = getItemPosition(it)
                        onClickListener.invoke(getItem(position))
                    }
                }
            }
        }
    }

    override fun bind(binding: ViewDataBinding, item: ITEM) {
        // ignore
        // use onBindViewHolder
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ViewDataBinding>, position: Int) {
        when (holder.binding) {
            is MakeLabelExistItemBinding -> {
                holder.binding.checkedLabel = getItem(position)
            }
            is MakeLabelNewItemBinding -> {
                Timber.d("makeLabelKeyword ${makeLabelKeyword?.makeNewLabelString(holder.binding.root.context)}")
                holder.binding.label = makeLabelKeyword?.makeNewLabelString(holder.binding.root.context)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.make_label_new_item
        } else {
            R.layout.make_label_exist_item
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return makeLabelKeyword != null
    }

    fun setMakeLabelVisible(search: String?) {
        val previousState = this.makeLabelKeyword
        val hadExtraRow = hasExtraRow()
        this.makeLabelKeyword = search
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != makeLabelKeyword) {
            notifyItemChanged(itemCount - 1)
        }
    }
}

private fun String?.makeNewLabelString(c: Context) = c.getString(R.string.make_label_new_label).format(this)
