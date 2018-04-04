package com.xinzy.zhihu.daily

import android.app.Application
import com.xinzy.http.LoggingInterceptor
import com.xinzy.http.SmartHttpConfig

/**
 * Created by Xinzy on 2018/3/21.
 */
class DailyApplication : Application() {

    companion object {
        private lateinit var sInstance: DailyApplication

        fun getInstance() = sInstance
    }


    override fun onCreate() {
        super.onCreate()

        sInstance = this
        val config = SmartHttpConfig.getInstance().with(this).debug(BuildConfig.DEBUG)
        if (BuildConfig.DEBUG) {
            config.certificates().hostnameVerifier().addNetworkInterceptor(LoggingInterceptor())
        }
        config.initHttpClient()
    }

}