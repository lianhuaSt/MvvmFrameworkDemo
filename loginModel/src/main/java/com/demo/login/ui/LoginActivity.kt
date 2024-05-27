package com.demo.login.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.common.base.activity.BaseMVVMActivity
import com.demo.common.route.ActivityType
import com.demo.common.util.KLog
import com.demo.login.BR
import com.demo.login.R
import com.demo.login.databinding.ActivityLoginBinding

@Route(path = ActivityType.login)
class LoginActivity : BaseMVVMActivity<ActivityLoginBinding, LoginVieModel>() {
    override fun getLayoutId() = R.layout.activity_login

    override fun getVariableId() = BR.viewModel

    override fun getType() = ActivityType.login

    override fun initView() {
        super.initView()


    }
}