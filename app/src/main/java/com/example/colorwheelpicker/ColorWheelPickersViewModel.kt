package com.example.colorwheelpicker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.colorwheelpicker.ColorWheelPickersContract.SelectedControl

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
class ColorWheelPickersViewModel : ColorWheelPickersContract.ViewModel() {

    private var _firstItemColor = MutableLiveData<Int>()
    override val firstItemColor: LiveData<Int> = _firstItemColor

    private var _secondItemColor = MutableLiveData<Int>()
    override val secondItemColor: LiveData<Int> = _secondItemColor

    private var _thirdItemColor = MutableLiveData<Int>()
    override val thirdItemColor: LiveData<Int> = _thirdItemColor

    private var _tintedCircleColor = MutableLiveData<Int>()
    override val tintedCircleColor: LiveData<Int> = _tintedCircleColor

    private var _itemColor = MutableLiveData<Int>()

    override fun initialize(firstItemColor: Int, secondItemColor: Int, thirdItemColor: Int) {
        _firstItemColor.value = firstItemColor
        _secondItemColor.value = secondItemColor
        _thirdItemColor.value = thirdItemColor
        _itemColor = _firstItemColor
    }

    override fun onTintedCircleSelected(control: SelectedControl) {
        _itemColor = when (control) {
            SelectedControl.FIRST -> _firstItemColor
            SelectedControl.SECOND -> _secondItemColor
            SelectedControl.THIRD -> _thirdItemColor
        }
        Log.d("xxxx", "onTintedCircleSelected - color=${_itemColor.value}")
        _tintedCircleColor.value = _itemColor.value
    }

    override fun onColorWheelPicked(color: Int) {
        _itemColor.value = color
    }
}