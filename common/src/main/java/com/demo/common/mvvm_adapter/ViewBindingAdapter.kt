package com.live.base.binding

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData


/**
 * View DataBinding 适配器
 */
@Suppress("unused")
class ViewBindingAdapter {

    companion object {


        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick")
        fun setViewOnClick(v: View, click: ((View) -> Unit)?) {
            v.setOnClickListener(click)
        }

        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick")
        fun setViewOnClick(v: View, click: (() -> Unit)?) {
            v.setOnClickListener {
                click?.invoke()
            }
        }


        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param listener 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick")
        fun setViewOnClick(v: View, listener: View.OnClickListener?) {
            v.setOnClickListener(listener)
        }

        /**
         * 根据资源 id 加载图片
         *
         * @param iv    [ImageView] 对象
         * @param resID 图片资源 id
         */
        @JvmStatic
        @BindingAdapter("android:src")
        fun src(iv: ImageView, resID: Int) {
            if (0 != resID) {
                iv.setImageResource(resID)
            }
        }


        @JvmStatic
        @BindingAdapter("android:edit_text")
        fun setEditText(edit: AppCompatEditText, str: MutableLiveData<String>) {
            str.value?.let {
                if (edit != null) {
                    var mCurrentStr = edit.getText().toString().trim();
                    if (!TextUtils.isEmpty(it)) {
                        if (!it.equals(mCurrentStr)) {
                            edit.setText(it);
                            // 设置光标位置
                            edit.setSelection(it.length);
                        }else{
                            return
                        }
                    }
                    edit.addTextChangedListener(object :TextWatcher{
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            str.value = s.toString()
                        }

                        override fun afterTextChanged(s: Editable?) {

                        }

                    })
                }
            }

        }


    }
}