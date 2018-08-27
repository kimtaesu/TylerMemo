package com.hucet.tyler.memo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hucet.tyler.memo.firebase.RemoteConfigProvider
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.ui.add.AddMemoActivity
import com.hucet.tyler.memo.ui.add.AddMemoFragment
import com.hucet.tyler.memo.ui.memo.MemoListFragment
import com.hucet.tyler.memo.vo.Memo
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var repository: MemoRepository

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.content, MemoListFragment.newInstance())
                    .commit()
        add_memo.setOnClickListener {
            Observable
                    .defer {
                        val memoId = repository.insertMemo(Memo.empty()) ?: 0
                        if (memoId <= 0)
                            TODO()

                        val memo = Memo.empty().apply {
                            id = memoId
                        }
                        Observable.just(memo)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        startActivity(AddMemoActivity.createIntent(this, it))
                    }
                    .also {
                        compositeDisposable.add(it)
                    }
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}
