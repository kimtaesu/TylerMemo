package com.hucet.tyler.memo.common

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class SpacesItemDecoration(private val mSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, recyclerView: RecyclerView, state: RecyclerView.State) {
        outRect.left = mSpacing
        outRect.top = mSpacing
        outRect.right = mSpacing
        outRect.bottom = mSpacing
    }
}