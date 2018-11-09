package com.hucet.tyler.memo.common

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hucet.tyler.memo.di.ManualInjectable
import dagger.android.support.AndroidSupportInjection


abstract class DaggerMviFragment<V : MvpView, P : MviPresenter<V, *>> : MviFragment<V, P>(), ManualInjectable {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}