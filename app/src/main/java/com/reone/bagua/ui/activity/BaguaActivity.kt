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
    }
}