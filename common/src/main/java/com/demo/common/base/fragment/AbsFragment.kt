package com.demo.common.base.fragment

import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

/**
 * @Description TODO
 * @Author sst
 * @Date 2023/2/23 5:17 下午
 */
open class AbsFragment : Fragment() {




    fun initBar(topbar: View) {
        ImmersionBar.with(this).statusBarView(topbar)
            .statusBarDarkFont(false)
            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            .keyboardEnable(true) //解决软键盘与底部输入框冲突问题
            .init()
    }

}