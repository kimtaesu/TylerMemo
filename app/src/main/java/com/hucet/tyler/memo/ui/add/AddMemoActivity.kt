package com.hucet.tyler.memo.ui.add

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.ui.color.ColorThemeFragment
import com.hucet.tyler.memo.ui.label.MakeLabelActivity
import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Memo
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_add_memo.*
import kotlinx.android.synthetic.main.view_add_memo_tools.view.*
import javax.inject.Inject

interface OnColorChangedListener {
    fun onColorChanged(colorTheme: ColorTheme)
}

class AddMemoActivity : AppCompatActivity(), HasSupportFragmentInjector, OnColorChangedListener {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    companion object {
        val TOOL_BOX_BACK_STACK_TAG = AddMemoActivity.javaClass.simpleName

        fun createIntent(c: Context?, memo: Memo): Intent {
            return Intent(c, AddMemoActivity::class.java).apply {
                putExtra(ArgKeys.KEY_MEMO.name, memo)
            }
        }
    }

    private val memo by lazy {
        intent?.getParcelableExtra(ArgKeys.KEY_MEMO.name) as Memo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memo)
        setSupportActionBar(toolbar)
        setToolbarColor(memo.colorTheme)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.content, AddMemoFragment.newInstance(memo))
                    .commit()

        add_memo_toolbox.label.setOnClickListener {
            startActivity(MakeLabelActivity.createIntent(this@AddMemoActivity, memo.id))
        }

        add_memo_toolbox.color_theme.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container_tools)
            if (fragment !is ColorThemeFragment) {
                supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                        .replace(R.id.container_tools, ColorThemeFragment.newInstance {
                            onColorChanged(it)
                        })
                        .addToBackStack(TOOL_BOX_BACK_STACK_TAG)
                        .commit()
            }
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onColorChanged(colorTheme: ColorTheme) {
        setToolbarColor(colorTheme)
        add_memo_toolbox.color_theme.setColorFilter(colorTheme.color.whiteInverseColor)
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

private val Int.whiteInverseColor: Int
    get() = if (this == Color.WHITE) Color.BLACK else this