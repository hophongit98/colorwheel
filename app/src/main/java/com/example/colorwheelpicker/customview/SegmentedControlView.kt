package com.example.colorwheelpicker.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.example.colorwheelpicker.R

class SegmentedControlView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val radiusPx: Float = RADIUS_DP * resources.displayMetrics.density

    private var defaultBackgroundColour = Color.parseColor("#2c2c2c")
    private var defaultCircleColour = Color.parseColor("#00c2a3")

    private var backgroundColour: Int = defaultBackgroundColour
    private var circleColour: Int = defaultCircleColour

    init {
        context.withStyledAttributes(attrs, R.styleable.SegmentedControlView) {
            backgroundColour = getColor(R.styleable.SegmentedControlView_backgroundColour, defaultBackgroundColour)
            circleColour = getColor(R.styleable.SegmentedControlView_circleColour, defaultCircleColour)
        }
    }

    override fun setBackgroundColor(color: Int) {
        backgroundColour = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawCircle(canvas)
    }

    fun resetBackgroundColour() {
        backgroundColour = defaultBackgroundColour
        invalidate()
    }

    fun setCircleColor(color: Int) {
        circleColour = color
        invalidate()
    }

    private fun drawBackground(canvas: Canvas) {
        backgroundPaint.color = backgroundColour
        canvas.drawPaint(backgroundPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        circlePaint.color = circleColour
        canvas.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radiusPx, circlePaint)
    }

    companion object {
        private const val RADIUS_DP = 20
    }
}