package com.reone.bagua.core.extension

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

/**
 * Created by wangxingsheng on 2018/3/26.
 *
 */
fun View.rxClickFirst(listener:()->Unit){
    RxView.clicks(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe {
                listener()
            }
}