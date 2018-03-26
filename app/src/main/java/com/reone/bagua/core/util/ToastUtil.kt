package com.reone.bagua.core.util

import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast

/**
 *
 * Toast工具类
 * 1.当有新的toast需要弹出的时候，会马上覆盖前一条的机制
 * 2.在任何线程中都能弹出的机制
 * Created by wangxingsheng on 2017/6/27.
 *
 */

class ToastUtil(private val appContext: Context) {

    private val toastTimer = { lastToast = "" }

    init {
        handler = Handler(appContext.mainLooper)
    }

    /**
     * toast三秒内相同内容不会显示多次
     */
    private fun handlerToastDismiss() {
        handler.removeCallbacks(toastTimer)
        handler.postDelayed(toastTimer, sameTime)
    }

    fun talk(text: String) {
        if (TextUtils.isEmpty(text)) return
        handler.post {
            if (lastToast == text) {
                return@post
            }
            mToast?.cancel()
            mToast = Toast.makeText(appContext, text, Toast.LENGTH_SHORT)
            mToast?.show()
            handlerToastDismiss()
            lastToast = text
        }
    }

    private fun getResourcesString(stringId: Int): String {
        return appContext.resources.getString(stringId)
    }

    fun talk(stringId: Int) {
        talk(getResourcesString(stringId))
    }

    companion object {

        private var lastToast = ""
        private lateinit var handler: Handler
        private var mToast: Toast? = null
        private const val sameTime: Long = 2000
    }

}
