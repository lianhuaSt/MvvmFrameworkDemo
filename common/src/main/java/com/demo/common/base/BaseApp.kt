package com.demo.common.base


import android.os.Bundle
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.demo.common.util.KLog

import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/22 5:19 下午
 */
open class BaseApp : MultiDexApplication() {


    companion object {
        private lateinit var instance: BaseApp
        fun getInstance(): BaseApp {
            return instance
        }
    }

    init {

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            val waterDropHeader = MaterialHeader(context)
            waterDropHeader.setColorSchemeColors(-0xab58)
            waterDropHeader
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance =  this
        KLog.e("route--日志开启")
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)

        ARouter.init(this)
    }


}