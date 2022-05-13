package com.example.colorwheelpicker.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.colorwheelpicker.ColorObservable
import com.example.colorwheelpicker.ColorObserver
import com.example.colorwheelpicker.R
import com.example.colorwheelpicker.customview.ColorWheelSelector.Companion.SELECTOR_RADIUS_DP
import kotlin.math.*

/**
 * Created by Phillip Truong
 * date 10/05/2022.
 */
class ColorWheelView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet), ColorObservable {

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f

    private val currentPoint = PointF()
    private var pickedColor = ContextCompat.getColor(this.context, R.color.teal)

    private var colorWheelPalette: ColorWheelPalette = ColorWheelPalette(context, attributeSet)
    private var colorWheelSelector: ColorWheelSelector = ColorWheelSelector(context)

    private lateinit var colorObserver: ColorObserver

    init {
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(colorWheelPalette, layoutParams)
        addView(colorWheelSelector, layoutParams)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        val maxHeight = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int = maxWidth.coerceAtMost(maxHeight)
        width = height
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val netWidth = w - paddingLeft - paddingRight
        val netHeight = h - paddingTop - paddingBottom
        radius = netWidth.coerceAtMost(netHeight) * 0.5f - SELECTOR_RADIUS_DP * resources.displayMetrics.density
        if (radius < 0) return
        centerX = netWidth * 0.5f
        centerY = netHeight * 0.5f
        setSelectorColor(pickedColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
        when (motionEvent?.action) {
            ACTION_DOWN, ACTION_MOVE -> {
                updateSelector(motionEvent.x, motionEvent.y, true)
                return true
            }
        }
        return super.onTouchEvent(motionEvent)
    }

    override fun observe(colorObserver: ColorObserver) {
        this.colorObserver = colorObserver
    }

    fun setSelectorColor(color: Int, shouldTriggerObserver: Boolean = false) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        val r = hsv[1] * radius
        val radian = (hsv[0] / 180f * Math.PI).toFloat()
        pickedColor = color
        updateSelector(
            (r * cos(radian) + centerX),
            (-r * sin(radian) + centerY),
            shouldTriggerObserver,
            false
        )
    }

    private fun updateSelector(
        eventX: Float,
        eventY: Float,
        shouldTriggerObserver: Boolean = false,
        shouldGetColorAtPoint: Boolean = true
    ) {
        var x = eventX - centerX
        var y = eventY - centerY
        val r = sqrt((x * x + y * y).toDouble())
        if (r > radius) {
            x *= (radius / r).toFloat()
            y *= (radius / r).toFloat()
        }
        currentPoint.x = x + centerX
        currentPoint.y = y + centerY
        if (shouldGetColorAtPoint) pickedColor = getColorAtPoint(eventX, eventY)
        colorObserver.onColorPicked(pickedColor, shouldTriggerObserver)
        colorWheelSelector.updateSelector(currentPoint, pickedColor)
    }

    private fun getColorAtPoint(eventX: Float, eventY: Float): Int {
        val x = eventX - centerX
        val y = eventY - centerY
        val r = sqrt((x * x + y * y).toDouble())
        val hsv = floatArrayOf(0f, 0f, 1f)
        hsv[0] = (atan2(y, -x) / Math.PI * 180).toFloat() + 180
        hsv[1] = max(0f, min(1f, (r / radius).toFloat()))

        return Color.HSVToColor(hsv)
    }
}