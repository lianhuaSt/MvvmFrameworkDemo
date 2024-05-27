package com.demo.common.base.model

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/22 4:02 下午
 */
open class BaseItemModel<VM : BaseViewModel>() {


    lateinit var mViewModel: VM

    constructor(viewModel: VM) : this() {
        mViewModel = viewModel
    }

}