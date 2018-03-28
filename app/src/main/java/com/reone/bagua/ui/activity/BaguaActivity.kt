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
        binding.bagua.sameSpeed = false
        binding.bagua.clockwise = false
        binding.bagua.layers.add("挂图", arrayOf("☰","☴","☵","☶","☷","☳","☲","☱"))
        binding.bagua.layers.add("天干", arrayOf("甲","乙","丙","丁","午","己","庚","辛","壬","癸"))
        binding.bagua.layers.add("地支", arrayOf("子","丑","寅","卯","辰","巳","无","为","申","酉","戌","亥"))
        binding.bagua.layers.add("八卦", arrayOf("乾","巽","坎","艮","坤","震","离","兑"))
        binding.bagua.layers.add("五行", arrayOf("金","木","水","火","土"))
        binding.bagua.layers.add("节气", arrayOf("立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至","小寒","大寒"))
    }
}