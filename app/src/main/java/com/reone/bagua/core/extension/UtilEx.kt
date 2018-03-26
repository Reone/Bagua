package com.reone.bagua.core.extension

import com.reone.bagua.core.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wangxingsheng on 2018/3/26.
 *
 */
fun toast(toast: String?) {
    App.talk(toast)
}

fun toast(toastRes: Int) {
    App.talk(toastRes)
}

fun <T> Observable<T>.ioToMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}