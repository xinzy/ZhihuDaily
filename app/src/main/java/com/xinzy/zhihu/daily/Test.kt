package com.xinzy.zhihu.daily

import android.app.Activity
import android.os.Bundle

/**
 * Created by Xinzy on 2018/3/27.
 */

class Test : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        val TEST = "TEST"

        fun get(): Any {
            return Any()
        }
    }
}
