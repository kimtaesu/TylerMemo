package com.hucet.tyler.memo.ui.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.ui.memo.MemoListFragment
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_simple_toolbar.*
import javax.inject.Inject

interface OnColorChangedListener {
    fun onColorChanged(colorTheme: ColorTheme)
}

class AddMemoActivity : AppCompatActivity(), HasSupportFragmentInjector, OnColorChangedListener {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    companion object {
        fun createIntent(c: Context?, memo: Memo? = null): Intent {
            return Intent(c, AddMemoActivity::class.java).apply {
                putExtra(ArgKeys.KEY_MEMO.name, memo)
            }
        }
    }

    private val memo by lazy {
        intent?.getParcelableExtra(ArgKeys.KEY_MEMO.name) as? Memo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_toolbar)
        setSupportActionBar(toolbar)
        setToolbarColor(memo?.colorTheme ?: ColorTheme.default.colorTheme)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.content, AddMemoFragment.newInstance())
                    .commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onColorChanged(colorTheme: ColorTheme) {
        setToolbarColor(colorTheme)
        animateRevealShow(toolbar)
    }

    private fun setToolbarColor(colorTheme: ColorTheme) {
        toolbar.setBackgroundColor(colorTheme.color)
        toolbar.setTitleTextColor(colorTheme.textColor)
    }

    private fun animateRevealShow(viewRoot: View) {
        val cx = (viewRoot.left + viewRoot.right) / 2
        val cy = (viewRoot.top + viewRoot.bottom) / 2
        val finalRadius = Math.max(viewRoot.width, viewRoot.height)

        val anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0f, finalRadius.toFloat())
        viewRoot.visibility = View.VISIBLE
        anim.duration = 500
        anim.interpolator = AccelerateInterpolator()
        anim.start()
    }
}
