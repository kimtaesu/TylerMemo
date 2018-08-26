package com.hucet.tyler.memo.ui.add

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.SpacesItemDecoration
import com.hucet.tyler.memo.utils.Util
import kotlinx.android.synthetic.main.fragment_color_theme.*

class AddMemoToolbox : ConstraintLayout {
    private val layoutInflater by lazy {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val v = layoutInflater.inflate(R.layout.view_add_memo_tools, this, false)
        addView(v)
    }
}