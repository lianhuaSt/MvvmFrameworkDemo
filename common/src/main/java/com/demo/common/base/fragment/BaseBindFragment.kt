package com.demo.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/23 5:30 下午
 */
abstract class BaseBindFragment<vb : ViewBinding> : AbsFragment() {

    lateinit var binding: vb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return initToView(inflater, container, savedInstanceState)
    }


    fun initToView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding()
        return binding.root
    }

    abstract fun getViewBinding(): vb


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    open fun initView() {

    }


    open fun initData() {

    }
}