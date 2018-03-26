package com.reone.bagua.core.util

import android.util.Log
import com.reone.bagua.BuildConfig

/**
 * Created by wangxingsheng on 2017/12/6.
 *
 */
object LOG {
    const val TAG = "reone"
    fun d(log:String?){
        if(BuildConfig.IS_DEBUG){
            Log.d(TAG,log)
        }
    }
    fun d(tag:String,log:String?){
        if(BuildConfig.IS_DEBUG){
            Log.d(TAG,tag+": " + log)
        }
    }
    fun e(log:String){
        if(BuildConfig.IS_DEBUG){
            Log.e(TAG,log)
        }
    }
    fun e(tag:String,log:String?){
        if(BuildConfig.IS_DEBUG){
            Log.e(TAG,tag+": " + log)
        }
    }
}