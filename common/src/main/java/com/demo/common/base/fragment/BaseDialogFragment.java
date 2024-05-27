package com.demo.common.base.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.demo.common.util.KLog;


/**
 * 基础弹框
 * Created by ian on 9/30/15.
 */
public class BaseDialogFragment extends DialogFragment {

    public static final String TAG = BaseDialogFragment.class.getSimpleName();

    private BaseDialogCallback baseDialogCallback;

    public void setBaseDialogCallback(BaseDialogCallback callback) {
        baseDialogCallback = callback;
    }


    public interface BaseDialogCallback {
        void onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.e(this.getClass().getName());
        Bundle bundle = getArguments();
        if (null != bundle) {// android.os.BadParcelableException，在解组数据前，加上bundle.setClassLoader(getClass().getClassLoader())，将恢复Apk classloader方式。
            bundle.setClassLoader(getClass().getClassLoader());
        }
        DialogFragmentManager.getInstance().addDialogFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private View view;
    private static final long ROOM_ANIM_TIME = 800;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fullScreen()) {
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            return;
        }
        if (needFullScreen()) {
            initWindow();
        }
    }

    public void startAnim() {
    }

    public void disAnim() {
        try {
            dismiss();
        } catch (Exception e) {
            KLog.e("--->" + e);
        }
    }


    private void initWindow() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置dialog背景窗口透明
        DisplayMetrics metrics = new DisplayMetrics();//显示度量
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);//设置到默认activity的窗口显示度量
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();//得到dialog的窗口布局参数属性
        params.width = metrics.widthPixels; //dialog的布局参数宽度是窗口的宽度像素
        params.height = metrics.heightPixels;//dialog的布局参数高度是窗口的高度像素
        params.gravity = Gravity.BOTTOM;//设置dialog靠上
        getDialog().getWindow().setAttributes(params);//把布局属性设置个dialog
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
    }


    protected boolean beDestroyed = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
        beDestroyed = true;
        DialogFragmentManager.getInstance().removeDialogFragment(this);



        if (baseDialogCallback != null) {
            baseDialogCallback.onDestroy();
        }
    }

    @Deprecated
    protected boolean needFullScreen() {
        return false;
    }

    //是否全屏
    public boolean fullScreen() {
        return false;
    }
}
