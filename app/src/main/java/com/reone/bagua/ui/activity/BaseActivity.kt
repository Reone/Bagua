package com.reone.bagua.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import com.hwangjr.rxbus.RxBus
import com.reone.bagua.R
import com.reone.bagua.databinding.ActivityBaseBinding

/**
 * Created by wangxingsheng on 2018/2/23.
 *
 */
@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private var closeAnim = false
    private lateinit var rootBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        RxBus.get().register(this)
    }

    protected fun <T : ViewDataBinding> setDataBinding(layoutResId: Int): T {
        return DataBindingUtil.inflate(LayoutInflater.from(this), layoutResId, rootBinding.container, true)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        closeAnim = false
    }

    override fun finish() {
        super.finish()
        if (!closeAnim) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            closeAnim = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!closeAnim) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            closeAnim = true
        }
    }
}