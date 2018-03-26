package com.reone.bagua.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by wangxingsheng on 2018/3/26.
 *
 */
class BaguaView: View {
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        
    }
}