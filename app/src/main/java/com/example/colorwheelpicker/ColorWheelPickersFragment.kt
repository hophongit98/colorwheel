package com.example.colorwheelpicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.colorwheelpicker.ColorWheelPickersContract.SelectedControl
import com.example.colorwheelpicker.base.BaseContract
import com.example.colorwheelpicker.base.BaseFragment
import com.example.colorwheelpicker.databinding.FragmentColorWheelPickersBinding

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
class ColorWheelPickersFragment private constructor() : BaseFragment(), View.OnClickListener {
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
            scvFirstItem.setOnClickListener(this@ColorWheelPickersFragment)
            scvSecondItem.setOnClickListener(this@ColorWheelPickersFragment)
            scvThirdItem.setOnClickListener(this@ColorWheelPickersFragment)
            colorWheelView.observe(object : ColorObserver {
                override fun onColorPicked(color: Int, shouldTrigger: Boolean) {
                    if (shouldTrigger) {
                        colorWheelPickersViewModel.onColorWheelPicked(color)
                    }
                }
            })
        }
    }

    override fun initViewModel() {
        val firstItemColor = binding.scvFirstItem.getCircleColor()
        val secondItemColor = binding.scvSecondItem.getCircleColor()
        val thirdItemColor = binding.scvThirdItem.getCircleColor()
        colorWheelPickersViewModel = createViewModel<ColorWheelPickersViewModel>().apply {
            initialize(firstItemColor, secondItemColor, thirdItemColor)
        }
    }

    override fun observeViewModel(viewModel: BaseContract.BaseViewModel) {
        with(viewModel as ColorWheelPickersContract.ViewModel) {
            firstItemColor.observe(viewLifecycleOwner) {
                binding.scvFirstItem.setCircleColor(it)
            }
            secondItemColor.observe(viewLifecycleOwner) {
                binding.scvSecondItem.setCircleColor(it)
            }
            thirdItemColor.observe(viewLifecycleOwner) {
                binding.scvThirdItem.setCircleColor(it)
            }
            tintedCircleColor.observe(viewLifecycleOwner) {
                updateSelector(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(viewId: View?) {
        when (viewId?.id) {
            R.id.scv_first_item -> {
                colorWheelPickersViewModel.onTintedCircleSelected(SelectedControl.FIRST)
                updateBackgroundItemSelected(SelectedControl.FIRST)
            }
            R.id.scv_second_item -> {
                colorWheelPickersViewModel.onTintedCircleSelected(SelectedControl.SECOND)
                updateBackgroundItemSelected(SelectedControl.SECOND)
            }
            R.id.scv_third_item -> {
                colorWheelPickersViewModel.onTintedCircleSelected(SelectedControl.THIRD)
                updateBackgroundItemSelected(SelectedControl.THIRD)
            }
        }
    }

    private fun updateBackgroundItemSelected(selectedControl: SelectedControl) {
        with(binding) {
            when (selectedControl) {
                SelectedControl.FIRST -> {
                    scvFirstItem.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.selected_element_background
                        )
                    )
                    scvSecondItem.resetBackgroundColour()
                    scvThirdItem.resetBackgroundColour()
                }
                SelectedControl.SECOND -> {
                    scvSecondItem.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.selected_element_background
                        )
                    )
                    scvFirstItem.resetBackgroundColour()
                    scvThirdItem.resetBackgroundColour()
                }
                SelectedControl.THIRD -> {
                    scvThirdItem.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.selected_element_background
                        )
                    )
                    scvSecondItem.resetBackgroundColour()
                    scvFirstItem.resetBackgroundColour()
                }
            }
        }
    }

    private fun updateSelector(color: Int) {
        binding.colorWheelView.setSelectorColor(color)
    }

    companion object {
        fun newInstance() = ColorWheelPickersFragment()
    }
}