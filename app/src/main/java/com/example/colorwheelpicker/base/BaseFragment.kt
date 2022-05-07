package com.example.colorwheelpicker.base

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment : Fragment() {

    protected lateinit var viewModel: BaseContract.BaseViewModel

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView(view, savedInstanceState)
        initViewModel()
        observeViewModel(viewModel)
    }

    protected open fun initializeView(view: View, savedInstanceState: Bundle?) {}

    protected open fun initViewModel() {}

    protected open fun observeViewModel(viewModel: BaseContract.BaseViewModel) {}

    protected inline fun <reified ViewModelT : BaseContract.BaseViewModel> createViewModel() =
        ViewModelProvider(this).get(ViewModelT::class.java).also { viewModel = it }
}