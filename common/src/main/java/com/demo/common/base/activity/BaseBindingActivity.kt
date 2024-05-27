package com.demo.common.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.demo.common.util.KLog

/**
 * @Description TODO  非mvvm模式 适用于简单的页面
 * @Author sst
 * @Date 2023/2/22 2:59 下午
 */
abstract class BaseBindingActivity<vb : ViewBinding> : BaseActivity() {

    lateinit var binding: vb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        KLog.e("sst","--onCreate--")
        setContentView(binding.root)
        initBefore()
        initParam()
        initView()
        initData()
    }



    abstract fun getViewBinding(): vb





}