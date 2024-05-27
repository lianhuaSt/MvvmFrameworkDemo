package com.demo.common.base.activity

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter

import com.gyf.immersionbar.ImmersionBar
import com.demo.common.R
import com.demo.common.base.ActivityMangerHelper
import com.demo.common.base.fragment.BaseDialogFragment

import com.demo.common.util.KLog

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import java.lang.ref.WeakReference

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/22 1:59 下午
 */
abstract class BaseActivity : RxAppCompatActivity(), IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this) //注入路由参数

        super.onCreate(savedInstanceState)
        ActivityMangerHelper.addActivity(this)

    }

    var isResume: Boolean = false

    override fun onResume() {
        super.onResume()
        isResume = true
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    fun initBar(topbar: View, isDarkFont: Boolean = false) {
        ImmersionBar.with(this).statusBarView(topbar)
            .statusBarDarkFont(isDarkFont)
            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            .keyboardEnable(true) //解决软键盘与底部输入框冲突问题
            .init()
    }

    fun showFrgmtDlg(frgmt: BaseDialogFragment) {
        if (frgmt == null) {
            return;
        }
        try {
            val ft = supportFragmentManager.beginTransaction()
            if (!frgmt.isAdded) {
                frgmt.show(ft, "dialog")
            }
        } catch (ex: Exception) {
            KLog.e("---->" + ex)
        }
    }




    override fun initViewObservable() {

    }

    override fun initData() {

    }

    override fun initView() {

    }


    override fun initParam() {

    }

    override fun initBefore() {

    }



    fun isAlive(): Boolean {
        return !isFinishing && !isDestroyed
    }

    abstract fun getType(): String


    override fun onDestroy() {
        super.onDestroy()
        ActivityMangerHelper.removeActivity(this)
    }
}