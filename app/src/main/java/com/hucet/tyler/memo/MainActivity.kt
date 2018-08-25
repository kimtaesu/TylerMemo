package com.hucet.tyler.memo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hucet.tyler.memo.firebase.RemoteConfigProvider
import com.hucet.tyler.memo.ui.add.AddMemoActivity
import com.hucet.tyler.memo.ui.add.AddMemoFragment
import com.hucet.tyler.memo.ui.memo.MemoListFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.content, MemoListFragment.newInstance())
                    .commit()
        add_memo.setOnClickListener {
            startActivity(AddMemoActivity.createIntent(this))
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
