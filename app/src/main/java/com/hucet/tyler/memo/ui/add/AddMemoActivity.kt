package com.hucet.tyler.memo.ui.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.ui.memo.MemoListFragment

class AddMemoActivity : AppCompatActivity() {
    companion object {
        fun createIntent(c: Context?): Intent {
            return Intent(c, AddMemoActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, AddMemoFragment.newInstance())
                    .commit()
    }
}