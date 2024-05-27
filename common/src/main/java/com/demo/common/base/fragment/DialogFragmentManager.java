package com.demo.common.base.fragment;

import android.app.Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用弹窗管理类
 * 避免业务弹窗太多重叠，管理混乱
 */
public class DialogFragmentManager {

    private static  DialogFragmentManager instance;
    private List<BaseDialogFragment> dialogFragments = new ArrayList<>();

    public static  DialogFragmentManager getInstance() {
        if (null == instance) {
            instance = new  DialogFragmentManager();
        }
        return instance;
    }

    public void addDialogFragment(BaseDialogFragment dialog) {
        if (null != dialog) {
            dialogFragments.add(dialog);
        }
    }

    public void removeDialogFragment(BaseDialogFragment dialog) {
        if (null != dialog) {
            dialogFragments.remove(dialog);
        }
    }

    public boolean isShowDialog() {
        for (BaseDialogFragment dialogFragment : dialogFragments) {
            Dialog dialog = dialogFragment.getDialog();
            if (null != dialog && dialog.isShowing()) {
                return true;
            }
        }
        return false;
    }

    public void clearDialog() {
        for (BaseDialogFragment dialog : dialogFragments) {
            dialog.dismiss();
        }
        dialogFragments.clear();
    }
}
