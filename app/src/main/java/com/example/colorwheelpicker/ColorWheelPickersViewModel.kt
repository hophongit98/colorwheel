package com.example.colorwheelpicker

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.colorwheelpicker.ColorWheelPickersContract.SelectedControl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    private var _tintedCirclePosition = MutableLiveData<Pair<PointF, Int>>()
    override val tintedCirclePosition: LiveData<Pair<PointF, Int>> = _tintedCirclePosition

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
                getPositionOfParticularColor(_firstItemColor.value)
                _firstItemColor
            }
            SelectedControl.SECOND -> {
                getPositionOfParticularColor(_secondItemColor.value)
                _secondItemColor
            }
            SelectedControl.THIRD -> {
                getPositionOfParticularColor(_thirdItemColor.value)
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

    private fun getPositionOfParticularColor(color: Int?) {
        if (color == null) return

        coroutineJob = CoroutineScope(Dispatchers.Default).launch {
            first@ for (y in 0 until bitmap.width) {
                for (x in 0 until bitmap.height) {
                    val pixel: Int = bitmap.getPixel(x, y)
                    val redValue: Int = Color.red(pixel)
                    val blueValue: Int = Color.blue(pixel)
                    val greenValue: Int = Color.green(pixel)

                    val pixelColor: Int = Color.rgb(redValue, greenValue, blueValue)
                    if (pixelColor == color) {
                        _tintedCirclePosition.postValue(Pair(PointF(x.toFloat(), y.toFloat()), color))
                        break@first
                    }
                }
            }
        }
    }
}