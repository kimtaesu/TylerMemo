package com.hucet.tyler.memo.ui.add

import android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.di.ManualInjectable
import com.hucet.tyler.memo.ui.color.ColorThemeFragment
import com.hucet.tyler.memo.vo.Memo
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_add_memo.*
import kotlinx.android.synthetic.main.view_add_memo_tools.view.*
import timber.log.Timber
import javax.inject.Inject

class AddMemoFragment : MviFragment<AddMemoView, AddMemoPresenter>(), ManualInjectable, AddMemoView {

    companion object {
        fun newInstance(): AddMemoFragment {
            return AddMemoFragment()
        }
    }

    @Inject
    lateinit var presenter: AddMemoPresenter

    override fun createPresenter(): AddMemoPresenter = presenter

    private val createMemoSubject = PublishSubject.create<Memo>()
    override fun createMemo(): Observable<Memo> = createMemoSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddMemoBinding>(
                inflater,
                R.layout.fragment_add_memo,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        add_memo_text.hint = RandomGreetingHintGenerator.generate()
        button.setOnClickListener {
            val newMemo = Memo("", add_memo_text.text.toString())
            createMemoSubject.onNext(newMemo)
        }
        add_memo_toolbox.color_theme.setOnClickListener {
            activity?.run {
                val fragment = supportFragmentManager.findFragmentById(R.id.container_tools)
                if (fragment !is ColorThemeFragment) {
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                            .replace(R.id.container_tools, ColorThemeFragment.newInstance(), "loginFragment")
                            .addToBackStack("loginFragment")
                            .commit()
                } else {
                    supportFragmentManager.popBackStack("loginFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
//                    supportFragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
//                            .replace(R.id.container_tools, Fragment()).commitAllowingStateLoss()
//
//                    val exitFade = Fade()
//                    exitFade.duration = 1000
//                    fragment.setExitTransition(exitFade)
//                    fragment.returnTransition = exitFade
//
//                    val f = Fragment()
//                    val enterFade = Fade()
//                    enterFade.setStartDelay(2000)
//                    enterFade.setDuration(100)
//                    f.setEnterTransition(enterFade)
//                    supportFragmentManager.beginTransaction()
//                            .replace(R.id.container_tools, f).commitAllowingStateLoss()
                }
            }
        }
    }

    override fun render(state: AddMemoState) {
        Timber.d("state ${state}")
        when {
            state.createdMemo -> {
                activity?.finish()
            }
        }
    }
}