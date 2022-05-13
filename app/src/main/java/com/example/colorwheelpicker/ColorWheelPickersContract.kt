package com.example.colorwheelpicker

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
        abstract val tintedCircleColor: LiveData<Int>

        abstract fun initialize(firstItemColor: Int, secondItemColor: Int, thirdItemColor: Int)
        abstract fun onTintedCircleSelected(control: SelectedControl)
        abstract fun onColorWheelPicked(color: Int)
    }

    enum class SelectedControl {
        FIRST,
        SECOND,
        THIRD
    }
}