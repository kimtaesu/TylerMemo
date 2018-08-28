package com.hucet.tyler.memo.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.repository.MemoRepository
import com.hucet.tyler.memo.ui.add.AddMemoActivity
import com.hucet.tyler.memo.ui.memo.MemoListFragment
import com.hucet.tyler.memo.vo.Memo
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MviActivity<MainView, MainPresenter>(), MainView, HasSupportFragmentInjector {
    override fun clearIntents(): Observable<Unit> = clearIntentsSubject

    override fun createMemo(): Observable<Any> = RxView.clicks(add_memo)

    override fun createPresenter(): MainPresenter = presenter

    private val clearIntentsSubject = PublishSubject.create<Unit>()
    @Inject
    lateinit var presenter: MainPresenter
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
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun render(state: MainState) {
        Timber.d("render: $state")
        when (state) {
            is MainState.CreatedMemo -> {
                clearIntentsSubject.onNext(Unit)
                startActivity(AddMemoActivity.createIntent(this, state.memo))
            }
            is MainState.FailCreateMemo -> {
                Toast.makeText(this, state.errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
