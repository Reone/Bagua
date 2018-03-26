package com.reone.bagua.core

import android.app.Application
import android.content.Context
import com.reone.bagua.core.util.LOG
import com.reone.bagua.core.util.ToastUtil

/**
 * Created by wangxingsheng on 2018/3/26.
 *
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        private val toastUtil: ToastUtil by lazy {
            ToastUtil(appContext)
        }

        fun talk(toast: String?) {
            LOG.d("talk","APP talk toast " + toast)
            toastUtil.talk(toast.toString())
        }

        fun talk(toastRes: Int) {
            toastUtil.talk(toastRes)
        }

        lateinit var appContext: Context
    }
}