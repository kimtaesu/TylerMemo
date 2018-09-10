package com.hucet.tyler.memo.common


import android.support.annotation.MenuRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View

typealias OnActionItemClickListener = (MenuItem) -> Unit

class PrimaryActionModeCallback : ActionMode.Callback {

    var onActionItemClickListener: OnActionItemClickListener? = null

    private var mode: ActionMode? = null
    @MenuRes
    private var menuResId: Int = 0
    private var title: String? = null
    private var subtitle: String? = null

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        this.mode = mode
        mode.menuInflater.inflate(menuResId, menu)
        mode.title = title
        mode.subtitle = subtitle
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        this.mode = null
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        onActionItemClickListener?.invoke(item)
        return true
    }

    fun startActionMode(activity: AppCompatActivity,
                        @MenuRes menuResId: Int,
                        title: String? = null,
                        subtitle: String? = null,
                        listener: OnActionItemClickListener? = null) {
        this.menuResId = menuResId
        this.title = title
        this.subtitle = subtitle
        onActionItemClickListener = listener
        activity.startSupportActionMode(this)
    }

    fun finishActionMode() {
        mode?.finish()
    }
}