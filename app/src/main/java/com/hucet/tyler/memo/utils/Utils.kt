package com.hucet.tyler.memo.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

object Utils {
    fun dpToPx(c: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.resources.displayMetrics).toInt()
    }

    fun getPixels(unit: Int, size: Float): Int {
        val metrics = Resources.getSystem().getDisplayMetrics()
        return TypedValue.applyDimension(unit, size, metrics).toInt()
    }
}


