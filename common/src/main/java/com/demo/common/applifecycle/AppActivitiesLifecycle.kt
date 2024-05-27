package com.demo.common.applifecycle;
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import java.lang.ref.SoftReference
import java.util.*


class AppActivitiesLifecycle private constructor() : ActivityLifecycleCallbacks {

    companion object {
        @JvmStatic
        val instance: AppActivitiesLifecycle by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { AppActivitiesLifecycle() }

        private const val TAG = "ActivityLifeCycle"
    }

    /**
     * 最新的 展示的 activity 永远在第一个
     */
    private val activities = ArrayList<SoftReference<Activity>>()

    private var appForeground = false // app是否在前台

    private var activitiesNum = 0
    private var startedActivitiesNum = 0

    private val extraLifecycleCallbacks = ArrayList<SoftReference<AppActivityLifeCycleCallback?>>()

    fun getActivities(): ArrayList<Activity> {
        val list = ArrayList<Activity>()
        activities.forEach {
            val ac = it.get()
            if (ac != null)
                list.add(ac)
        }
        return list
    }

    fun getActivitiesNum(): Int {
        return activitiesNum
    }

    /**
     * 当前resume状态的activity。可以是任何activity（包括三方activity）
     *
     * @return
     */
    val curActivity: Activity?
        get() = if (activities.isNullOrEmpty()) null else activities.first().get()

    /**
     * 结束所有
     * @param keepActivity 保留的activity
     */
    @JvmOverloads
    fun finishAll(keepActivity: Activity? = null) {
        val finishTempList = ArrayList<Activity?>()
        activities.forEach {
            if (it.get() != null) {
                if (!(keepActivity != null && keepActivity == it.get()))
                    finishTempList.add(it.get())
            }
        }
        finishTempList.forEach { it?.finish() }
    }

    /**
     * 结束某一个区间的activities
     * 比如传 1～7 那就是 列表下标 1到7 会finish
     * startIndex endIndex 都是include
     */
    fun finishActivitiesBySection(startIndex: Int, endIndex: Int) {
        try {
            val list = getActivities()
            if (startIndex < 0 || endIndex > list.size) {
                return
            }
            val finishList = list.subList(startIndex, endIndex + 1)
            finishList.forEach { it.finish() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 结束某一组下标
     */
    fun finishActivitiesByIndex(vararg indexs: Int) {
        try {
            val list = getActivities()
            val finishList = ArrayList<Activity>()
            indexs.forEach {
                try {
                    val ac = list[it]
                    finishList.add(ac)
                } catch (e: Exception) {
                }
            }
            finishList.forEach { it.finish() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 增加置额外的生命周期回调 执行自己的回调
     *
     * @param callback
     */
    fun addExtraLifecycleCallback(callback: AppActivityLifeCycleCallback?) {
        if (callback != null) {
            removeExtraLifecycleCallback(callback)
            extraLifecycleCallbacks.add(SoftReference(callback))
        }
    }

    /**
     * 移除回调
     */
    @SuppressLint("NewApi")
    fun removeExtraLifecycleCallback(callback: AppActivityLifeCycleCallback?) {
        if (callback != null) {
            val iterator = extraLifecycleCallbacks.iterator()
            while (iterator.hasNext()) {
                try {
                    // 类相同或者类名字相同都会被移除
                    val instance = iterator.next().get()
                    val insName = instance?.javaClass?.name
                    val cbName = callback.javaClass.name
                    if (Objects.equals(instance, callback)
                        || TextUtils.equals(insName, cbName)
                    ) {
                        iterator.remove()
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG, activity.javaClass.simpleName + " onActivityCreated")

        activitiesNum++
        Log.i(TAG, "activities num = $activitiesNum")

        activities.add(0, SoftReference(activity))

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivityCreated(activity, savedInstanceState)
                it.get()?.onActivitiesNumChange(activitiesNum)
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, activity.javaClass.simpleName + " onActivityStarted")

        startedActivitiesNum++
        notifyAppForeground(startedActivitiesNum > 0, activity)

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivityStarted(activity)
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, activity.javaClass.simpleName + " onActivityResumed")

        runCatching {
            if (activities.isEmpty()) {
                return@runCatching
            }
            if (activities.first().get() != null && activity == activities.first().get()) {
                return@runCatching
            }
            var activityRef: SoftReference<Activity>? = null
            activities.forEach {
                if (it.get() == activity) {
                    activityRef = it
                    return@forEach
                }
            }
            if (activityRef != null) {
                activities.remove(activityRef!!)
                activities.add(0, activityRef!!)
            }
        }

        AppContext.setActivityInResume(activity.javaClass.simpleName)

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivityResumed(activity)
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityPaused(activity: Activity) {
        Log.w(TAG, activity.javaClass.simpleName + " onActivityPaused")

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivityPaused(activity)
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityStopped(activity: Activity) {
        Log.w(TAG, activity.javaClass.simpleName + " onActivityStopped")

        startedActivitiesNum--
        notifyAppForeground(startedActivitiesNum > 0, activity)

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivityStopped(activity)
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.w(TAG, activity.javaClass.simpleName + " onActivitySaveInstanceState")

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivitySaveInstanceState(activity, outState)
            } catch (e: Exception) {
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.e(TAG, activity.javaClass.simpleName + " onActivityDestroyed")
        activitiesNum--
        Log.i(TAG, "activities num = $activitiesNum")

        runCatching {
            var activityRef: SoftReference<Activity>? = null
            activities.forEach {
                if (it.get() == activity) {
                    activityRef = it
                    return@forEach
                }
            }
            if (activityRef != null) {
                activities.remove(activityRef!!)
            }
        }

        if (activitiesNum == 0) {
            activities.clear()
        }

        extraLifecycleCallbacks.forEach {
            try {
                it.get()?.onActivityDestroyed(activity)
                it.get()?.onActivitiesNumChange(activitiesNum)
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 通知 前后台切换
     *
     * @param foreground
     * @param activity
     */
    private fun notifyAppForeground(foreground: Boolean, activity: Activity) {
        if (foreground != appForeground) {
            // 有变化回调
            Log.d("AppContext", "AppInForeground = $foreground")

            AppContext.setAppInForeground(foreground)
            appForeground = foreground

            extraLifecycleCallbacks.forEach {
                try {
                    it.get()?.onAppForeground(foreground, activity)
                } catch (e: Exception) {
                }
            }
        }
    }
}