package com.hucet.tyler.memo.ui.add

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.tyler.memo.R
import com.hucet.tyler.memo.databinding.FragmentAddMemoBinding
import com.hucet.tyler.memo.databinding.FragmentMemoListBinding

class AddMemoFragment : Fragment() {
    companion object {
        fun newInstance(): AddMemoFragment {
            return AddMemoFragment()
        }
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

    }
}