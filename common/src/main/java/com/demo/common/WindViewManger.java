package com.demo.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.demo.common.base.BaseApp;

/**
 * @Description //顶部视图管理类
 * @Author sst
 * @Date 2023/7/17 2:54 PM
 */
public class WindViewManger {
    private WindViewManger() {
        init();

    }

    private static WindViewManger instance;

    public static WindViewManger getInstance() {
        if (instance == null) {
            synchronized (WindViewManger.class) {
                if (instance == null) {
                    instance = new WindViewManger();
                }
            }
        }
        return instance;
    }


    private ViewGroup rootView;

    private void init() {
        rootView = new FrameLayout(BaseApp.Companion.getInstance());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public void addView(View view) {
        rootView.addView(view);
    }


    public void addWindView(Activity activity) {
        if (activity != null && rootView != null) {
            ViewParent parent = rootView.getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(rootView);
            }

            activity.getWindow().addContentView(rootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void removeView(View view) {
       rootView.removeView(view);
    }

    public void removeWindView(Activity activity) {
        if (rootView != null) {
            ViewParent parent = rootView.getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(rootView);
            }

        }
    }
}
