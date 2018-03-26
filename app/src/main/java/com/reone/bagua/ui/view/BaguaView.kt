package com.reone.bagua.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.cos
import kotlin.math.min
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
    var rotating = false
    val layers = Layers()
    private val yinPaint = Paint()
    private val yangPaint = Paint()
    private val fontPaint = Paint()
    private val framePaint = Paint()
    private val frameMaxWidth = 40f
    private val frameMargin = 4f
    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initPaint()
    }

    /**
     * 保证view为正方形
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec))
        val measureSpec = View.MeasureSpec.makeMeasureSpec(min(measuredWidth,measuredHeight), View.MeasureSpec.EXACTLY)
        super.onMeasure(measureSpec, measureSpec)
    }

    /**
     * 初始阴阳画笔
     */
    private fun initPaint() {
        yinPaint.color = Color.BLACK
        yinPaint.style = Paint.Style.FILL
        yinPaint.isAntiAlias = true
        yinPaint.isDither = true
        yangPaint.color = Color.WHITE
        yangPaint.style = Paint.Style.FILL
        yangPaint.isAntiAlias = true
        yangPaint.isDither = true
        fontPaint.color = Color.YELLOW
        framePaint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerSize = layers.data().size
        val layerWidth = layerSize * frameMaxWidth
        for(i in layerSize - 1 .. 0){
            layers.getData(i)?.let {
                drawLayer(canvas, it, start, i%2 != 0, i)
            }
        }
        drawYinyang(canvas,layerWidth,width.toFloat()-layerWidth,layerWidth,height.toFloat() - layerWidth, start)
        if(rotating){
            start = (start + 1f)%360f
            invalidate()
        }
    }

    /**
     * 每一层
     * @param start 起始角度
     * @param clockwise 选择方向是否顺时针
     * @param index 从内向外数，此层所处位置
     */
    private fun drawLayer(canvas: Canvas, array: Array<String>, start: Float = 0f, clockwise: Boolean = true, index: Int) {
        val size = array.size
    }

    /**
     * 阴阳图
     * @param start 基准角度，x轴正方向为0
     */
    private fun drawYinyang(canvas: Canvas, l:Float, r:Float, t:Float, b:Float,start:Float = 0f) {
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

    inner class Layers{
        private val layerKey = ArrayList<String>()
        private val layerData = HashMap<String,Array<String>>()
        /**
         * 按顺序获取数据集合
         */
        fun data(): List<Array<String>> {
            val data = ArrayList<Array<String>>()
            for (name in layerKey){
                getData(name)?.let {
                    data.add(it)
                }
            }
            return data
        }

        /**
         * 移动数据位置
         */
        fun move(src: Int,target: Int){
            layerKey.add(target,layerKey.removeAt(src))
        }

        /**
         * 获取名字集合
         */
        fun names():List<String>{
            return layerKey
        }

        /**
         * 添加一组数据,如果名字存在则覆盖数据
         * @param num 获取集合时，会根据此值排序
         * @param name 要加入的数据组的名字
         * @param data 数据组
         */
        fun add(name:String,data:Array<String>){
            if(!layerData.containsKey(name) && !layerKey.contains(name)){
                layerKey.add(name)
            }
            layerData[name] = data
        }

        /**
         * 通过名字查询数据
         * @param name
         */
        fun getData(name:String):Array<String>?{
            return layerData[name]
        }

        /**
         * 通过位置获取数据
         */
        fun getData(num:Int):Array<String>?{
            if(num >= 0 && num < layerKey.size){
                return layerData[layerKey[num]]
            }
            return null
        }

        /**
         * 通过位置获取名称
         */
        fun getName(num:Int):String?{
            if(num >= 0 && num < layerKey.size){
                return layerKey[num]
            }
            return null
        }

    }
}