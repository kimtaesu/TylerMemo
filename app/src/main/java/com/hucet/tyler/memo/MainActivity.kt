package com.hucet.tyler.memo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.hucet.tyler.memo.ui.label.ColorLabelFragment
import com.hucet.tyler.memo.ui.list.MemoListFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, MemoListFragment.newInstance())
                    .commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
