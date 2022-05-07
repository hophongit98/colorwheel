package com.example.colorwheelpicker

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.colorwheelpicker.ColorWheelPickersContract.SelectedControl
import kotlinx.coroutines.Job

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

    private var _pickedColor = MutableLiveData<Int>()
    private var coroutineJob: Job? = null

    private lateinit var bitmap: Bitmap

    override fun initialize(firstItemColor: Int, secondItemColor: Int, thirdItemColor: Int , bitmap: Bitmap) {
        _firstItemColor.value = firstItemColor
        _secondItemColor.value = secondItemColor
        _thirdItemColor.value = thirdItemColor

        _pickedColor = _firstItemColor
        this.bitmap = bitmap
    }

    override fun onTintedCircleSelected(control: SelectedControl) {
        _pickedColor = when (control) {
            SelectedControl.FIRST -> {
                _firstItemColor
            }
            SelectedControl.SECOND -> {
                _secondItemColor
            }
            SelectedControl.THIRD -> {
                _thirdItemColor
            }
        }
    }

    override fun onColorWheelPicked(color: Int) {
        _pickedColor.value = color
    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob?.cancel()
    }

}