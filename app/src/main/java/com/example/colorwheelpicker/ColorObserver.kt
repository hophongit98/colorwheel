package com.example.colorwheelpicker

/**
 * Created by Phillip Truong
 * date 10/05/2022.
 */
interface ColorObserver {
    /**
     * Color has changed.
     *
     * @param color the new color
     * @param shouldTrigger should this event be triggered to the observers (you can ignore this)
     */
    fun onColorPicked(color: Int, shouldTrigger: Boolean)
}