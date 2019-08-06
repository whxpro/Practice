package com.whx.practice.view.bezier

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by whx on 2018/1/16.
 */
class Bezier @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defArr: Int = 0) : View(context, attrs, defArr) {

    private var centerX = 0f
    private var centerY = 0f

    private var start: PointF = PointF(0f, 0f)
    private var end: PointF = PointF(0f, 0f)
    private var control: PointF = PointF(0f, 0f)

    private val mPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 8f
            textSize = 60f
        }
    }
    private val mPath = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w.toFloat()/2
        centerY = h.toFloat()/2

        // 设置开始和结束的数据点坐标
        start.x = centerX - 200
        start.y = centerY

        end.x = centerX + 200
        end.y = centerY

        control.x = centerX
        control.y = centerY - 100
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 20f

        // 画各个点
        canvas.drawPoint(start.x, start.y, mPaint)
        canvas.drawPoint(end.x, end.y, mPaint)
        canvas.drawPoint(control.x, control.y, mPaint)

        // 画辅助线
        mPaint.strokeWidth = 4f
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint)
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint)

        // 画贝塞尔曲线
        mPaint.color = Color.RED
        mPaint.strokeWidth = 8f

        mPath.reset()
        mPath.moveTo(start.x, start.y)
        mPath.quadTo(control.x, control.y, end.x, end.y)

        canvas.drawPath(mPath, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 根据触摸位置更新控制点，并重绘
        control.x = event.x
        control.y = event.y

        invalidate()
        return true
    }
}