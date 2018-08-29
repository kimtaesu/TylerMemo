package com.hucet.tyler.memo.ui.label

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.common.toMemo
import com.hucet.tyler.memo.databinding.ActivityMakeLabelBinding
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.toBundle
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_make_label.*
import kotlinx.android.synthetic.main.activity_make_label.view.*
import javax.inject.Inject

interface SearchView {
    fun searchView(): Observable<CharSequence>
}

class MakeLabelActivity : AppCompatActivity(), HasSupportFragmentInjector, SearchView {
    companion object {
        fun createIntent(c: Context?, memo: Memo): Intent {
            return Intent(c, MakeLabelActivity::class.java).apply {
                putExtras(memo.toBundle())
            }
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private val memo by lazy {
        intent.toMemo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        DataBindingUtil.setContentView<ActivityMakeLabelBinding>(this, R.layout.activity_make_label)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.content, MakeLabelFragment.newInstance(memo)).commit()
        }
    }

    override fun searchView(): Observable<CharSequence> = RxTextView.textChanges(toolbar.search)
}