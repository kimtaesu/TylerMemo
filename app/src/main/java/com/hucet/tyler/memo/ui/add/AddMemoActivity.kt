package com.hucet.tyler.memo.ui.add

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.PrimaryActionModeCallback
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.repository.colortheme.ColorThemeRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import com.hucet.tyler.memo.ui.color.ColorThemeFragment
import com.hucet.tyler.memo.ui.label.MakeLabelActivity
import com.hucet.tyler.memo.utils.AppExecutors
import com.hucet.tyler.memo.utils.RevealAnimationUtils
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_add_memo.*
import kotlinx.android.synthetic.main.view_add_memo_tools.*
import kotlinx.android.synthetic.main.view_add_memo_tools.view.*
import javax.inject.Inject

interface ColorThemeView {
    fun onColorChanged(colorTheme: ColorTheme)
}

class AddMemoActivity : AppCompatActivity(), HasSupportFragmentInjector, ColorThemeView, AddMemoNavigation {
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

    @Inject
    lateinit var appExecutors: AppExecutors

    private val toolboxAdapter by lazy {
        ToolboxAdapter(appExecutors)
    }
    private val memo by lazy {
        intent.getParcelableExtra(ArgKeys.KEY_MEMO.name) as Memo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memo)
        setSupportActionBar(toolbar)
        setToolbarColor(ColorTheme.default.colorTheme)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, AddMemoFragment.newInstance(memo))
                    .commit()

        add_memo_toolbox.label.setOnClickListener {
            navigateMakeLabel()
        }

        color_theme.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container_tools)
            if (fragment !is ColorThemeFragment) {
                supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                        .replace(R.id.container_tools, ColorThemeFragment.newInstance())
                        .addToBackStack(TOOL_BOX_BACK_STACK_TAG)
                        .commit()
            }
        }
        check_items.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.content) as? AddMemoFragment
            fragment?.onClickedCheckItems(true)
//            primaryActionModeCallback.startActionMode(this, R.menu.action_check_item, getString(R.string.check_list_action_title),
//                    listener = {
//                        when (it.itemId) {
//                            R.id.add -> {
//                                fragment?.onAddCheckItem()
//                            }
//                        }
//                    })
            supportFragmentManager?.popBackStack(AddMemoActivity.TOOL_BOX_BACK_STACK_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }


    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onColorChanged(colorTheme: ColorTheme) {
        setToolbarColor(colorTheme)
        (supportFragmentManager.findFragmentById(R.id.content) as? ColorThemeView)?.onColorChanged(colorTheme)
    }

    private fun setToolbarColor(colorTheme: ColorTheme) {
        toolbar.setBackgroundColor(colorTheme.color)
        toolbar.setTitleTextColor(colorTheme.textColor)
        RevealAnimationUtils.animateRevealShow(toolbar)
    }

    override fun navigateMakeLabel() {
        startActivity(MakeLabelActivity.createIntent(this@AddMemoActivity, memo))
    }
}