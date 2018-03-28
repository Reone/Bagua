package com.reone.bagua.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.reone.bagua.core.util.LOG
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
    private val whitePaint = Paint()
    /**
     * 每层环带宽度
     */
    private val frameMaxHeight = 90f
    /**
     * 阴阳鱼最小半径
     */
    private val yinyangMinR = 20f
    /**
     * 环带直接的间隔宽度
     */
    private val frameMargin = 12f
    /**
     * 文字尺寸
     */
    private val fontSize = 40f
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
        fontPaint.color = Color.BLACK
        fontPaint.style = Paint.Style.STROKE
        fontPaint.isAntiAlias = true
        fontPaint.isDither = true
        fontPaint.textSize = fontSize
        fontPaint.textAlign = Paint.Align.CENTER
        framePaint.color = Color.WHITE
        framePaint.style = Paint.Style.FILL
        framePaint.isAntiAlias = true
        framePaint.isDither = true
        whitePaint.color = Color.WHITE
        whitePaint.style = Paint.Style.FILL
        whitePaint.isAntiAlias = true
        whitePaint.isDither = true
    }

    private var layerSize = 0
    private var layerHeight = 0f
    private var layerWidth = 0f

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        layerSize = layers.data().size
        layerHeight = frameMaxHeight
        if(layerSize * layerHeight > (width - yinyangMinR * 2f)){
            layerHeight = (width - yinyangMinR * 2f)/layerSize
        }
        layerWidth = layerSize * layerHeight
        LOG.d(TAG,"" +
                "\nframeMaxHeight:$frameMaxHeight"+
                "\nyinyangMinR:$yinyangMinR"+
                "\nframeMargin$frameMargin"+
                "\nfontSize$fontSize"+
                "\nwidth:$width " +
                "\nheight:$height " +
                "\nlayerSize:$layerSize " +
                "\nlayerHeight:$layerHeight " +
                "\nlayerWidth:$layerWidth")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //从最外层开始画
        for(i in 0 until layerSize){
            layers.getData(i)?.let {
                //clockwise 表达式保证包括阴阳鱼每一层旋转方向不同
                drawLayer(canvas, it, start, layerHeight,(layerSize - i) % 2 == 0, i)
            }
        }
        drawYinyang(canvas,layerWidth,width.toFloat()-layerWidth,layerWidth,height.toFloat() - layerWidth, start)
        if(rotating){
            start = (start + 1f) % 360f
            invalidate()
        }
    }

    /**
     * 每一层
     * @param start 起始角度
     * @param clockwise 选择方向是否顺时针
     * @param index 从外到内数，此层所处位置
     */
    private fun drawLayer(canvas: Canvas, array: Array<String>, start: Float = 0f, layerHeight:Float,clockwise: Boolean = true, index: Int) {
        val size = array.size
        //圆心
        val x = width / 2f
        val y = height / 2f
        //外层半径
        val r1 = width / 2f - frameMargin / 2f - index * layerHeight
        //内层半径
        val r2 = width / 2f - layerHeight + frameMargin / 2f - index * layerHeight
        //文字半径
        val rf = ( r1 + r2 ) / 2f - fontSize / 2f
        val fontRectf = RectF(x - rf,y - rf ,x + rf , y + rf)
        val frame = Path()
        frame.addCircle(x,y,r1,Path.Direction.CW)
        frame.addCircle(x,y,r2,Path.Direction.CCW)
        canvas.drawPath(frame,framePaint)
        val fontPath = Path()
        for (i in 0 until size){
            var startAngle = 360f / size * i - 90f - 360f / size / 2f
            if(clockwise){
                startAngle += start
            }else{
                startAngle -= start
            }
            fontPath.reset()
            fontPath.addArc(fontRectf,startAngle,360f / size)
            canvas.drawTextOnPath(array[i],fontPath,0f,0f,fontPaint)
        }


    }

    /**
     * 阴阳鱼
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
            for (name in names()){
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
                layerKey.add(0,name)
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