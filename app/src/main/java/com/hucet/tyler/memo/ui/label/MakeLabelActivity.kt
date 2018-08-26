package com.hucet.tyler.memo.ui.label

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.UNKNOWN_ID
import com.hucet.tyler.memo.databinding.ActivityMakeLabelBinding
import com.hucet.tyler.memo.repository.LabelRepository
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.vo.Memo
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
        fun createIntent(c: Context?, memoId: Long): Intent {
            return Intent(c, MakeLabelActivity::class.java).apply {
                putExtra(ArgKeys.KEY_MEMO_ID.name, memoId)
            }
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var repository: MemoRepository

    private val memoId by lazy {
        intent.getLongExtra(ArgKeys.KEY_MEMO_ID.name, UNKNOWN_ID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        DataBindingUtil.setContentView<ActivityMakeLabelBinding>(this, R.layout.activity_make_label)
        if (savedInstanceState == null) {
            if (memoId != UNKNOWN_ID)
                supportFragmentManager.beginTransaction().add(R.id.content, MakeLabelFragment.newInstance(memoId)).commit()
            else
                TODO("memoId != UNKNOWN_ID")
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun searchView(): Observable<CharSequence> = RxTextView.textChanges(toolbar.search)
}