package com.demo.userLib

import android.content.Context
import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.common.route.ServiceType
import com.demo.common.route.service.UserIProvider


@Route(path = ServiceType.user_service)
class UserImp : UserIProvider {
    override fun login(name: String, password: String): Boolean {
        if (TextUtils.equals("张三", name) && TextUtils.equals("123", password)) {
            return true
        }
        return false
    }

    override fun init(p0: Context?) {

    }

}