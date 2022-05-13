package com.example.colorwheelpicker.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Phillip Truong
 * date 10/05/2022.
 */
class ColorWheelPalette(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f

    private val huePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val shaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val netWidth = w - paddingLeft - paddingRight
        val netHeight = h - paddingTop - paddingBottom
        radius = netWidth.coerceAtMost(netHeight) * 0.5f
        if (radius < 0) return
        centerX = w * 0.5f
        centerY = h * 0.5f

        huePaint.shader = SweepGradient(
            centerX, centerY,
            intArrayOf(Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED),
            null
        )

        shaderPaint.shader = RadialGradient(
            centerX, centerY, radius,
            Color.WHITE, 0x00FFFFFF,
            Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centerX, centerY, radius, huePaint)
        canvas?.drawCircle(centerX, centerY, radius, shaderPaint)
    }
}