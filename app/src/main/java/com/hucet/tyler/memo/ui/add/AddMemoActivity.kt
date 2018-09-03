package com.hucet.tyler.memo.ui.add

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.toMemo
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.ui.color.ColorThemeFragment
import com.hucet.tyler.memo.ui.label.MakeLabelActivity
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.toBundle
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_memo.*
import kotlinx.android.synthetic.main.view_add_memo_tools.view.*
import javax.inject.Inject

interface ColorThemeView {
    fun onColorChanged(colorTheme: ColorTheme)
    fun onColorClose()
}

class AddMemoActivity : AppCompatActivity(), HasSupportFragmentInjector, ColorThemeView, AddMemoNavigation {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var repository: MemoRepository

    companion object {
        val TOOL_BOX_BACK_STACK_TAG = AddMemoActivity.javaClass.simpleName
        fun createIntent(c: Context?, memo: Memo): Intent {
            return Intent(c, AddMemoActivity::class.java).apply {
                putExtras(memo.toBundle())
            }
        }
    }

    private val memo by lazy {
        intent.toMemo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memo)
        setSupportActionBar(toolbar)
        setToolbarColor(memo.colorTheme)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, AddMemoFragment.newInstance(memo.id))
                    .commit()

        add_memo_toolbox.label.setOnClickListener {
            navigateMakeLabel()
        }

        add_memo_toolbox.color_theme.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container_tools)
            if (fragment !is ColorThemeFragment) {
                supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                        .replace(R.id.container_tools, ColorThemeFragment.newInstance())
                        .addToBackStack(TOOL_BOX_BACK_STACK_TAG)
                        .commit()
            }
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onColorChanged(colorTheme: ColorTheme) {
        Observable
                .defer {
                    repository.updateColorTheme(colorTheme, memo.id)
                    Observable.just(true)
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun onColorClose() {
        supportFragmentManager.popBackStack(TOOL_BOX_BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun setToolbarColor(colorTheme: ColorTheme) {
        toolbar.setBackgroundColor(colorTheme.color)
        toolbar.setTitleTextColor(colorTheme.textColor)
    }

    override fun navigateMakeLabel() {
        startActivity(MakeLabelActivity.createIntent(this@AddMemoActivity, memo))
    }
}

private val Int.whiteInverseColor: Int
    get() = if (this == Color.WHITE) Color.BLACK else this