package com.reone.bagua.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawYinyang(canvas,0f,width.toFloat(),0f,height.toFloat())
    }

    /**
     * 阴阳图
     */
    @SuppressLint("NewApi")
    private fun drawYinyang(canvas: Canvas, l:Float, r:Float, t:Float, b:Float) {
        //初始阴阳画笔
        val yinPaint = Paint()
        yinPaint.color = Color.BLACK
        yinPaint.style = Paint.Style.FILL
        yinPaint.isAntiAlias = true
        yinPaint.isDither = true
        val yangPaint = Paint()
        yangPaint.color = Color.WHITE
        yangPaint.style = Paint.Style.FILL
        yangPaint.isAntiAlias = true
        yangPaint.isDither = true
        //阴阳底色
        val yinyang = RectF(l,t,r,b)
        canvas.drawArc(yinyang,-90f,180f,false,yinPaint)
        canvas.drawArc(yinyang,90f,180f,false,yangPaint)
        //阴阳弧
        canvas.drawCircle(r/2,b/4*3,b/4,yinPaint)
        canvas.drawCircle(r/2,b/4,b/4,yangPaint)
        //阴阳眼
        canvas.drawCircle(r/2,b/4,b/16,yinPaint)
        canvas.drawCircle(r/2,b/4*3,b/16,yangPaint)
    }
}