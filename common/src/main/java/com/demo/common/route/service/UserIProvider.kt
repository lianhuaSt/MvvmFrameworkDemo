package com.demo.common.route.service

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/4/27 4:33 PM
 */

interface UserIProvider : IProvider {
    fun login(name: String, password: String): Boolean


}