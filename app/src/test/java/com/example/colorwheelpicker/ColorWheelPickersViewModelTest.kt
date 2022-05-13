package com.example.colorwheelpicker

/**
 * Created by Phillip Truong
 * date 12/05/2022.
 */

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.example.colorwheelpicker.ColorWheelPickersContract.SelectedControl

@RunWith(MockitoJUnitRunner::class)
class ColorWheelPickersViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModelTest: ColorWheelPickersContract.ViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModelTest = ColorWheelPickersViewModel()
    }

    @Test
    fun givenFirstItemIsSelected_whenOnColorWheelPicked_thenFirstItemReceiveValue() {
        // given
        viewModelTest.onTintedCircleSelected(SelectedControl.FIRST)

        // when
        viewModelTest.onColorWheelPicked(123456)

        // then
        assertEquals(123456, viewModelTest.firstItemColor.value)
        assertEquals(null, viewModelTest.secondItemColor.value)
        assertEquals(null, viewModelTest.thirdItemColor.value)
    }

    @Test
    fun givenSecondItemIsSelected_whenOnColorWheelPicked_thenFirstItemReceiveValue() {
        // given
        viewModelTest.onTintedCircleSelected(SelectedControl.SECOND)

        // when
        viewModelTest.onColorWheelPicked(123456)

        // then
        assertEquals(null, viewModelTest.firstItemColor.value)
        assertEquals(123456, viewModelTest.secondItemColor.value)
        assertEquals(null, viewModelTest.thirdItemColor.value)
    }

    @Test
    fun givenThirdItemIsSelected_whenOnColorWheelPicked_thenFirstItemReceiveValue() {
        // given
        viewModelTest.onTintedCircleSelected(SelectedControl.THIRD)

        // when
        viewModelTest.onColorWheelPicked(123456)

        // then
        assertEquals(null, viewModelTest.firstItemColor.value)
        assertEquals(null, viewModelTest.secondItemColor.value)
        assertEquals(123456, viewModelTest.thirdItemColor.value)
    }
}