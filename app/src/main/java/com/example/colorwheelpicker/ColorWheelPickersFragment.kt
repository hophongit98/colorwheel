package com.example.colorwheelpicker

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.colorwheelpicker.base.BaseContract
import com.example.colorwheelpicker.base.BaseFragment
import com.example.colorwheelpicker.databinding.FragmentColorWheelPickersBinding

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
class ColorWheelPickersFragment private constructor() : BaseFragment(), View.OnClickListener, View.OnTouchListener {
    private var _binding: FragmentColorWheelPickersBinding? = null
    private val binding get() = _binding!!

    private lateinit var colorWheelPickersViewModel: ColorWheelPickersContract.ViewModel

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

            ivColorWheel.setOnTouchListener(this@ColorWheelPickersFragment)
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