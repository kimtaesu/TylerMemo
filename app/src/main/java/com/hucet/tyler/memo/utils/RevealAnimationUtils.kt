package com.hucet.tyler.memo.utils

import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator

object RevealAnimationUtils {
    fun animateRevealShow(viewRoot: View, interpolator: Interpolator = AccelerateInterpolator(), duration: Long = 500) {
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        val finalRadius = Math.max(viewRoot.width, viewRoot.height)

        val anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0f, finalRadius.toFloat())
        viewRoot.visibility = View.VISIBLE
        anim.duration = duration
        anim.interpolator = interpolator
        anim.start()
    }
}