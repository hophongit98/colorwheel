package com.example.colorwheelpicker.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
interface BaseContract {

    abstract class BaseViewModel : ViewModel(), CoroutineScope {

        private val viewModelCoroutineSupervisor = SupervisorJob()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main.immediate + viewModelCoroutineSupervisor

        override fun onCleared() {
            super.onCleared()
            viewModelCoroutineSupervisor.cancel()
        }
    }
}