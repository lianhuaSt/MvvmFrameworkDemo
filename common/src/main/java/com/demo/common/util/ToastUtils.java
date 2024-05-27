package com.demo.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.common.R;
import com.demo.common.base.BaseApp;

import java.lang.ref.WeakReference;


public class ToastUtils {
    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static WeakReference<View> sViewWeakReference;

    private ToastUtils() {
    }

    /**
     * @param message 弹出的内容
     */
    public static void showToast(String message) {
        sHandler.post(() -> showToast(message, Toast.LENGTH_SHORT));
    }

    /**
     * @param message 弹出的内容
     */
    public static void showToast(String message, long delayed) {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast(message, Toast.LENGTH_SHORT);
            }
        }, delayed);
    }


    /**
     * @param message 弹出长时间toast
     */
    public static void showLongToast(String message) {
        sHandler.post(() -> showToast(message, Toast.LENGTH_LONG));
    }

    /**
     * 弹出吐司
     *
     * @param message  显示文本
     * @param duration 显示时长
     */
    private static void showToast(String message, int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        cancel();
        LayoutInflater inflate = (LayoutInflater) BaseApp.Companion.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sViewWeakReference = new WeakReference<>(inflate.inflate(R.layout.toast_layout, null));
        if (sViewWeakReference != null) {
            final View view = sViewWeakReference.get();
            if (view != null) {
                sToast = new Toast(BaseApp.Companion.getInstance());
                TextView tv = view.findViewById(R.id.tv_content);
                tv.setText(message);
                sToast.setView(view);
                sToast.setDuration(duration);
                sToast.show();
            }

        }
    }

    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

}
