package com.demo.live.app

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.demo.common.base.activity.BaseActivity
import com.demo.common.route.ActivityType

@Route(path = ActivityType.demo)
class DemoActivity:BaseActivity() {
    override fun getType(): String {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
    }
}