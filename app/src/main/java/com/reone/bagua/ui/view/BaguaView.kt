package com.reone.bagua.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.reone.bagua.core.util.LOG
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by wangxingsheng on 2018/3/26.
 *
 */
class BaguaView: View {
    private val TAG = "BaguaView"
    companion object {
        var start = 0f
    }
    private var rotating = false
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawYinyang(canvas,0f,width.toFloat(),0f,height.toFloat(), start)
        if(rotating){
            start = (start + 1f)%360f
            invalidate()
        }
    }

    /**
     * 阴阳图
     * @param start 基准角度，x轴正方向为0
     */
    private fun drawYinyang(canvas: Canvas, l:Float, r:Float, t:Float, b:Float,start:Float) {
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
        canvas.drawArc(yinyang,start-90f,180f,false,yinPaint)
        canvas.drawArc(yinyang,start+90f,180f,false,yangPaint)
        //阴阳弧半径
        val r1 = ( b - t ) / 4
        //阴阳眼半径
        val r2 = r1 / 4
        //阴阳底圆心
        val x = ( r + l ) / 2
        val y = ( b + t ) / 2
        //起始偏移角度
        val angle = start/180*Math.PI.toFloat()
        //阴眼圆心
        val x1 = r1 * sin(angle) + x
        val y1 = y - r1 * cos(angle)
        //阳眼圆心
        val x2 = x - r1 * sin(angle)
        val y2 = r1 * cos(angle) + y
        //阴阳弧
        canvas.drawCircle(x2,y2,r1,yinPaint)
        canvas.drawCircle(x1,y1,r1,yangPaint)
        //阴阳眼
        canvas.drawCircle(x1,y1,r2,yinPaint)
        canvas.drawCircle(x2,y2,r2,yangPaint)
    }
}