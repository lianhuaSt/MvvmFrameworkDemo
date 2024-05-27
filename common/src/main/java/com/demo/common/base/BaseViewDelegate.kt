package com.demo.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.demo.common.base.activity.BaseActivity
import com.demo.common.base.fragment.AbsFragment

/**
 * Desc: 将view层拆解成不同的 delegate 去写 减少view层（activity/fragment）某个文件代码庞大
 */
abstract class BaseActivityDelegate : LifecycleObserver {
    protected var mActivity: BaseActivity? = null

    /**
     * 自动unregister
     */
    fun <Delegate : BaseActivityDelegate> register(activity: BaseActivity): Delegate {
        mActivity = activity
        mActivity?.lifecycle?.addObserver(this)
        return this as Delegate
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        if (mActivity != null) {
            mActivity?.lifecycle?.removeObserver(this)
            mActivity = null
        }
    }

    /**
     * 当前宿主状态
     */
    fun getCurrentState(): Lifecycle.State {
        return mActivity?.lifecycle?.currentState ?: Lifecycle.State.DESTROYED
    }

    /**
     * 是否存活 必须create完成
     */
    fun isAlive(): Boolean {
        return getCurrentState().isAtLeast(Lifecycle.State.CREATED)
    }

//    /**
//     * CoroutineScope
//     */
//    fun getActivityCoroutineScope(): LifecycleCoroutineScope? {
//        return mActivity?.lifecycleScope
//    }
}

abstract class BaseFragmentDelegate() : LifecycleObserver {
    protected var mFragment: AbsFragment? = null
    protected var viewLifecycleOwner: LifecycleOwner? = null

    /**
     * 一定要在 onViewCreated 之后，destroyView之前使用
     */
    fun <Delegate : BaseFragmentDelegate> register(fragment: AbsFragment): Delegate {
        mFragment = fragment
        try {
            viewLifecycleOwner = mFragment?.viewLifecycleOwner
            viewLifecycleOwner?.lifecycle?.addObserver(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this as Delegate
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onActivityCreated() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroyView() {
        if (mFragment != null && viewLifecycleOwner != null) {
            viewLifecycleOwner?.lifecycle?.removeObserver(this)
            mFragment = null
            viewLifecycleOwner = null
        }
    }

    /**
     * 当前宿主状态
     */
    fun getCurrentState(): Lifecycle.State {
        return viewLifecycleOwner?.lifecycle?.currentState ?: Lifecycle.State.DESTROYED
    }

    /**
     * 是否存活 必须create完成
     */
    fun isAlive(): Boolean {
        return getCurrentState().isAtLeast(Lifecycle.State.CREATED)
    }


}