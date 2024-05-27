package com.demo.live.app;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.common.WindViewManger;
import com.demo.common.applifecycle.AppActivityLifeCycleCallback;


/**
 * @Description TODO
 * @Author sst
 * @Date 2023/7/12 4:52 PM
 */
public class LifecycleCallbacks implements AppActivityLifeCycleCallback {
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        AppActivityLifeCycleCallback.super.onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        AppActivityLifeCycleCallback.super.onActivityStarted(activity);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        AppActivityLifeCycleCallback.super.onActivityResumed(activity);
        if (!activity.getComponentName().getClassName().contains("LiveRoomActivity")) {
            WindViewManger.getInstance().addWindView(activity);
//            FloatingView.get().attach(activity);
//            FloatingMagnetView floatingMagnetView = FloatingView.get().getView();
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        AppActivityLifeCycleCallback.super.onActivityPaused(activity);
        WindViewManger.getInstance().removeWindView(activity);
      //  FloatingView.get().detach(activity);
    }
}
