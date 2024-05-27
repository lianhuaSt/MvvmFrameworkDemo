package com.demo.common.base

import android.app.Activity
import com.demo.common.base.activity.BaseActivity
import java.util.*

/**
 * @Description TODO   activity 管理器
 * @Author sst
 * @Date 2023/4/26 5:09 PM
 */
object ActivityMangerHelper {


    private var activityStack: Stack<BaseActivity>? = null

    fun getTopActivityInstance(): BaseActivity? {
        if (activityStack != null) {
            var activity: BaseActivity
            for (i in activityStack!!.indices.reversed()) {
                activity = activityStack!![i]
                if (!activity.isFinishing()) {
                    return activity
                }
            }
        }
        return null
    }


    fun getSecondActivityInstance(): BaseActivity? {
        if (activityStack != null) {
            if (activityStack!!.size < 1) {
                return null
            }
            var activity: BaseActivity
            var tag = 0
            for (i in activityStack!!.indices.reversed()) {
                activity = activityStack!![i]
                if (!activity.isFinishing()) {
                    if (tag == 1) {
                        return activity
                    }
                    tag++
                }
            }
        }
        return null
    }


    fun getStackSize(): Int {
        return if (null == activityStack) 0 else activityStack!!.size
    }

    fun addActivity(activity: BaseActivity) {
        if (activityStack == null) {
            activityStack = Stack<BaseActivity>()
        }
        if (!activityStack!!.contains(activity)) {
            activityStack!!.push(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        if (activityStack != null) {
            activityStack!!.remove(activity)
        }
    }

    fun finishActivity(activityType: String) {
        if (null == activityStack) {
            return
        }
        val iterator: MutableIterator<BaseActivity> = activityStack!!.iterator()
        while (iterator.hasNext()) {
            val activity: BaseActivity = iterator.next()
            if (null != activity && activity.getType() === activityType) {
                activity.finish()
                iterator.remove()
            }
        }
    }

    fun hasActivity(activityType: String): Boolean {
        if (null == activityStack) {
            return false
        }
        val iterator: Iterator<BaseActivity> = activityStack!!.iterator()
        while (iterator.hasNext()) {
            val activity: BaseActivity = iterator.next()
            if (null != activity && activity.getType() === activityType) {
                return true
            }
        }
        return false
    }

    fun finishAllActivity() {
        if (activityStack == null) {
            return
        }
        val iterator: MutableIterator<BaseActivity> = activityStack!!.iterator()
        while (iterator.hasNext()) {
            val activity: BaseActivity = iterator.next()
            if (null != activity) {
                activity.finish()
            }
            iterator.remove()
        }
        activityStack!!.clear()
    }

    fun getActivityCount(): Int {
        return if (activityStack == null) 0 else activityStack!!.size
    }


    fun getActivityByType(type: String): BaseActivity? {
        if (activityStack == null) {
            return null
        }
        val iterator: Iterator<BaseActivity> = activityStack!!.iterator()
        while (iterator.hasNext()) {
            val activity: BaseActivity = iterator.next()
            if (null != activity && activity.getType() === type) {
                return activity
            }
        }
        return null
    }


}