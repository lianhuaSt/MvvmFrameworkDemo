package com.demo.common.applifecycle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.ref.SoftReference;

public class AppContext {
    private static boolean appInForeground;
    private static String activityInResume = "N/A";
    private static SoftReference<Context> context = new SoftReference<>(null);


    public static void init(Context ctx) {
        try {
            setContext(ctx instanceof Application ? ctx : (ctx.getApplicationContext() != null ? ctx.getApplicationContext() : ctx));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (ctx instanceof Application)
                ((Application) ctx).unregisterActivityLifecycleCallbacks(lifecycle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (ctx instanceof Application)
                ((Application) ctx).registerActivityLifecycleCallbacks(lifecycle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized static void setContext(Context c) {
        if (context != null && context.get() != null)
            context.clear();
        context = new SoftReference<>(c);
    }

    public static Context get() {
        Context ctx = context.get();
        if (ctx == null) {
            Log.w("ActivityLifeCycle", "Cannot find Application by context.get(), try invoke now application");
            init(invokeNowApplication());
        }
        return context.get();
    }

    public static Application invokeNowApplication() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Application app = (Application) activityThread.getMethod("getApplication").invoke(thread);
            if (app != null) {
                Log.i("ActivityLifeCycle", "Invoke now application success");
            }
            return app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AppActivitiesLifecycle lifecycle() {
        return AppActivitiesLifecycle.getInstance();
    }

    public static void addExtraLifecycleCallback(AppActivityLifeCycleCallback callback) {
        lifecycle().addExtraLifecycleCallback(callback);
    }

    public static void removeExtraLifecycleCallback(AppActivityLifeCycleCallback callback) {
        lifecycle().removeExtraLifecycleCallback(callback);
    }

    /**
     * 当前APP是否前后台
     *
     * @return
     */
    public static boolean isAppInForeground() {
        return appInForeground;
    }

    public static void setAppInForeground(boolean appInForeground) {
        AppContext.appInForeground = appInForeground;
    }

    public static void setActivityInResume(String activityInResume) {
        AppContext.activityInResume = activityInResume;
    }

    public static String getActivityInResume() {
        return activityInResume;
    }
}