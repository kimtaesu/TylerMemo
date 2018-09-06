package com.hucet.tyler.memo.ui.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.LabelItemsBinding
import com.hucet.tyler.memo.db.model.Label

class SummaryLabelsView : LinearLayout {
    private val DEFAULT_ELLIPSIZE_COUNT = 3

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private val inflater by lazy {
        LayoutInflater.from(context)
    }
    private var ellipsize: Int = DEFAULT_ELLIPSIZE_COUNT

    private fun initView(context: Context, attrs: AttributeSet?) {
        addView(inflater.inflate(R.layout.view_summary_labels, this, false))
        val a = context.obtainStyledAttributes(attrs, R.styleable.SummaryLabelsView)
        ellipsize = a.getInt(R.styleable.SummaryLabelsView_ellipsize, DEFAULT_ELLIPSIZE_COUNT)
        a.recycle()
    }

    fun setLabels(labels: List<Label>?) {
        labels?.forEach {
            val binding = DataBindingUtil.inflate<LabelItemsBinding>(inflater, R.layout.label_items, this, false)
            binding.label = it
            addView(binding.root)
        }
    }
}