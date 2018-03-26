package com.reone.bagua.ui.activity

import android.os.Bundle
import com.reone.bagua.R
import com.reone.bagua.databinding.ActivityBaguaBinding

/**
 * Created by wangxingsheng on 2018/3/26.
 *
 */
class BaguaActivity : BaseActivity() {
    private lateinit var binding:ActivityBaguaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setDataBinding(R.layout.activity_bagua)
        binding.bagua.rotating = true
        binding.bagua.layers.add("八卦", arrayOf("乾·天","巽·风","坎·水","艮·山","坤·地","震·雷","离·火","兑·泽"))
    }
}