package com.example.timemanager.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet

class CircleImageView : androidx.appcompat.widget.AppCompatImageView {
    //画笔
    private var mPaint: Paint? = null

    //圆形图片的半径
    private var mRadius = 0

    //图片的宿放比例
    private var mScale = 0f

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //由于是圆形，宽高应保持一致
        val size = Math.min(measuredWidth, measuredHeight)
        mRadius = size / 2
        setMeasuredDimension(size, size)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        mPaint = Paint()
        val drawable = drawable
        if (null != drawable) {
            val bitmap = (drawable as BitmapDrawable).bitmap

            //初始化BitmapShader，传入bitmap对象
            val bitmapShader =
                BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            //计算缩放比例
            mScale = mRadius * 2.0f / Math.min(bitmap.height, bitmap.width)
            val matrix = Matrix()
            matrix.setScale(mScale, mScale)
            bitmapShader.setLocalMatrix(matrix)
            mPaint!!.shader = bitmapShader
            //画圆形，指定好坐标，半径，画笔
            canvas.drawCircle(mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), mPaint!!)
        } else {
            super.onDraw(canvas)
        }
    }
}