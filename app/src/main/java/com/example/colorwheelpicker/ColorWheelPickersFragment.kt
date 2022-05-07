package com.example.colorwheelpicker

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.example.colorwheelpicker.base.BaseContract
import com.example.colorwheelpicker.base.BaseFragment
import com.example.colorwheelpicker.customview.ColorWheelSelector
import com.example.colorwheelpicker.databinding.FragmentColorWheelPickersBinding
import com.example.colorwheelpicker.util.absY
import com.example.colorwheelpicker.util.getBitmapFromView

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
class ColorWheelPickersFragment private constructor() : BaseFragment(), View.OnClickListener, View.OnTouchListener {
    private var _binding: FragmentColorWheelPickersBinding? = null
    private val binding get() = _binding!!

    private lateinit var colorWheelPickersViewModel: ColorWheelPickersContract.ViewModel
    private lateinit var colorWheelSelector: ColorWheelSelector
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColorWheelPickersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initializeView(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            with(layoutThreeWaySelectedControl) {
                scvFirstItem.setOnClickListener(this@ColorWheelPickersFragment)
                scvSecondItem.setOnClickListener(this@ColorWheelPickersFragment)
                scvThirdItem.setOnClickListener(this@ColorWheelPickersFragment)
            }

            linearLayoutRootView.setOnTouchListener(this@ColorWheelPickersFragment)

            colorWheelSelector = ColorWheelSelector(requireContext())

            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            root.addView(colorWheelSelector, layoutParams)
        }
    }

    override fun initViewModel() {
        val firstItemColor = ContextCompat.getColor(requireContext(), R.color.teal)
        val secondItemColor = ContextCompat.getColor(requireContext(), R.color.green)
        val thirdItemColor = ContextCompat.getColor(requireContext(), R.color.orange)

        val bitmap = (binding.ivColorWheel.drawable as BitmapDrawable).bitmap
        colorWheelPickersViewModel = createViewModel<ColorWheelPickersViewModel>().apply {
            initialize(firstItemColor, secondItemColor, thirdItemColor, bitmap)
        }
    }

    override fun observeViewModel(viewModel: BaseContract.BaseViewModel) {
        with(viewModel as ColorWheelPickersContract.ViewModel) {
            firstItemColor.observe(viewLifecycleOwner) {
                binding.layoutThreeWaySelectedControl.scvFirstItem.setCircleColor(it)
            }
            secondItemColor.observe(viewLifecycleOwner) {
                binding.layoutThreeWaySelectedControl.scvSecondItem.setCircleColor(it)
            }
            thirdItemColor.observe(viewLifecycleOwner) {
                binding.layoutThreeWaySelectedControl.scvThirdItem.setCircleColor(it)
            }
        }
    }

    override fun onClick(viewId: View?) {
        when (viewId?.id) {
            R.id.scv_first_item -> { }
            R.id.scv_second_item -> { }
            R.id.scv_third_item -> { }
        }
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (colorWheelSelector.isGone) {
            colorWheelSelector.visibility = View.VISIBLE
        }
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                bitmap = view.getBitmapFromView()
                bitmap?.let {
                    try {
                        val pixels = it.getPixel(motionEvent.x.toInt(), motionEvent.y.toInt())
                        val red = Color.red(pixels)
                        val green = Color.green(pixels)
                        val blue = Color.blue(pixels)
                        val color = Color.rgb(red, green, blue)

                        colorWheelPickersViewModel.onColorWheelPicked(color)
                        colorWheelSelector.updateSelector(
                            PointF(motionEvent.x, motionEvent.y),
                            color
                        )
                    } catch (e: Exception) {
                        Log.d("exception", e.message.toString())
                        colorWheelSelector.visibility = View.GONE
                    }
                }

                view.performClick()
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ColorWheelPickersFragment()
    }
}