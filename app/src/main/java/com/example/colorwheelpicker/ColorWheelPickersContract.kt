package com.example.colorwheelpicker

import android.graphics.Bitmap
import android.graphics.PointF
import androidx.lifecycle.LiveData
import com.example.colorwheelpicker.base.BaseContract

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
interface ColorWheelPickersContract {
    abstract class ViewModel : BaseContract.BaseViewModel() {
        abstract val firstItemColor: LiveData<Int>
        abstract val secondItemColor: LiveData<Int>
        abstract val thirdItemColor: LiveData<Int>
        abstract val tintedCirclePosition: LiveData<Pair<PointF, Int>>

        abstract fun initialize(firstItemColor: Int, secondItemColor: Int, thirdItemColor: Int , bitmap: Bitmap)
        abstract fun onTintedCircleSelected(control: SelectedControl)
        abstract fun onColorWheelPicked(color: Int)
    }

    enum class SelectedControl {
        FIRST,
        SECOND,
        THIRD
    }
}