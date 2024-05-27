package com.demo.common.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.demo.common.base.model.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/23 5:18 下午
 */
abstract class BaseMVVMFragment<DB : ViewDataBinding, VM : BaseViewModel> : AbsFragment(),
    LifecycleOwner {


    lateinit var binding: DB

    var viewModel: VM? = null

    private var viewModelId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return initViewObservable(inflater, container, savedInstanceState)
    }

    /**
     * ViewModel生命周期绑定
     */
    fun initViewObservable(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        viewModelId = getVariableId()

        if (viewModel == null) {

            val type = javaClass.genericSuperclass
            val modelClass: Class<BaseViewModel> = if (type is ParameterizedType) {
                type.actualTypeArguments[1] as Class<BaseViewModel>
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this.activity!!, modelClass) as VM
        }
        //绑定ViewModel
        binding.setVariable(viewModelId, viewModel)
        //让ViewModel拥有View的生命周期感应
        viewModel?.let { lifecycle.addObserver(it) }
        //注入Rx用的生命周期  保证不内存泄漏
        // viewModel?.injectLifecycleProvider(this)

        return binding.root
    }

    abstract fun getLayoutId(): Int

    abstract fun getVariableId(): Int


    override fun onDestroy() {
        super.onDestroy()
        viewModel?.let { lifecycle.removeObserver(it)
        }
        viewModel = null
        binding.unbind()
    }

    fun <T : ViewModel> createViewModel(fragment: FragmentActivity, modelClass: Class<T>): T? {
        return ViewModelProviders.of(fragment).get(modelClass)
    }
}