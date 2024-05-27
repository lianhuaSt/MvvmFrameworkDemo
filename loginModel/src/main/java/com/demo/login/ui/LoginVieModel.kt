package com.demo.login.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.common.base.model.BaseViewModel
import com.demo.common.route.ActivityType
import com.demo.common.route.ServiceType
import com.demo.common.route.service.UserIProvider
import com.demo.common.util.KLog
import com.demo.common.util.ToastUtils


class LoginVieModel(app: Application) : BaseViewModel(app) {

    val text = MutableLiveData("登录页")


    val name = MutableLiveData(" ")
    val password = MutableLiveData("")

    val login = View.OnClickListener {
        //使用route挎包调用

        val service:UserIProvider = ARouter.getInstance().build(ServiceType.user_service).navigation() as UserIProvider

        var login1 = service.login(name.value!!, password.value!!)
        if (login1){
            ToastUtils.showToast("登陆成功")
            //跳转页面
            ARouter.getInstance().build(ActivityType.demo).navigation()
        }else{
            ToastUtils.showToast("登陆失败")
        }
    }


}