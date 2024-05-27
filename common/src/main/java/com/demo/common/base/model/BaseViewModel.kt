package com.demo.common.base.model

import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

/**
 * @Description TODO
 * @Author sst
 * @Date 2022/4/14 4:01 下午
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseViewModel {




    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {

    }


    val finishActivity: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    /**
     * 仅 Fragment 使用
     */
    open fun onActivityCreated(savedInstanceState: Bundle?) {

    }

    /**
     * 从 Activity 返回且又返回数据
     */
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }




    override fun onCleared() {
        super.onCleared()
    }


}