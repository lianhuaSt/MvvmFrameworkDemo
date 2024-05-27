
package com.demo.common.applifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public interface AppActivityLifeCycleCallback extends Application.ActivityLifecycleCallbacks {

    @Override
    default void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    default void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    default void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    default void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    default void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    default void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    default void onActivityDestroyed(@NonNull Activity activity) {

    }

    /**
     * APP前后台切换
     *
     * @param foreground
     */
    default void onAppForeground(boolean foreground, Activity curActivity) {
    }

    /**
     * 存活的activities数量变化
     *
     * @param activitiesNum
     */
    default void onActivitiesNumChange(int activitiesNum) {
    }
}