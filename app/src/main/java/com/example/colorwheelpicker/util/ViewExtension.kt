package com.example.colorwheelpicker.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.getBitmapFromView(): Bitmap? {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}

fun View.absX() = IntArray(2).apply(::getLocationOnScreen).first()

fun View.absY() = IntArray(2).apply(::getLocationOnScreen).last()