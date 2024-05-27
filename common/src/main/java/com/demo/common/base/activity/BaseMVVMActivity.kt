package com.demo.common.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.common.base.model.BaseViewModel

import java.lang.reflect.ParameterizedType

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/22 1:59 下午
 */
abstract class BaseMVVMActivity<DB : ViewDataBinding, VM : BaseViewModel> :
    BaseActivity(),
    LifecycleOwner {
    var binding: DB? = null

    var viewModel: VM? = null

    private var viewModelId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewObservable(savedInstanceState)
        observerData()
        initView();
        initData();
    }


    open fun observerData() {
        viewModel?.finishActivity?.observe(this, Observer {
            finish()
        })
    }


    private fun initViewObservable(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView<DB>(this, getLayoutId())
        viewModelId = getVariableId()
        val type = javaClass.genericSuperclass
        val modelClass: Class<BaseViewModel> = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            BaseViewModel::class.java
        }
        viewModel = createViewModel(this, modelClass) as VM

        binding?.setVariable(viewModelId, viewModel)
        binding?.lifecycleOwner = this

        viewModel?.let { lifecycle.addObserver(it) }

    }

    abstract fun getLayoutId(): Int

    abstract fun getVariableId(): Int


    /**
     * 创建ViewModel
     */
    fun <T : ViewModel?> createViewModel(activity: FragmentActivity, cls: Class<T>): T? {
        return ViewModelProviders.of(activity).get(cls)
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel?.let { lifecycle.removeObserver(it) }
        viewModel = null
        binding?.unbind()
    }


}