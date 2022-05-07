package com.example.colorwheelpicker.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.View

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
class ColorWheelSelector(context: Context) : View(context) {

    private val selectorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val selectorRadiusPx: Float = SELECTOR_RADIUS_DP * resources.displayMetrics.density
    private val defaultStrokeWidth = 10f
    private val defaultBackgroundColour = Color.parseColor("#00c2a3")

    private var currentPoint = PointF(0F, 0F)
    private var backgroundColour: Int = defaultBackgroundColour

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        selectorPaint.apply {
            color = backgroundColour
            style = Paint.Style.FILL
        }
        canvas.drawCircle(currentPoint.x, currentPoint.y, selectorRadiusPx, selectorPaint)

        selectorPaint.apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = defaultStrokeWidth
        }
        canvas.drawCircle(currentPoint.x, currentPoint.y, selectorRadiusPx - defaultStrokeWidth, selectorPaint)
    }

    fun updateSelector(pointF: PointF, color: Int) {
        currentPoint = pointF
        backgroundColour = color
        invalidate()
    }

    companion object {
        private const val SELECTOR_RADIUS_DP = 30
    }
}