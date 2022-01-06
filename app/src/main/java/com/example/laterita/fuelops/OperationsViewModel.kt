package com.example.laterita.fuelops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OperationsViewModel : ViewModel() {
    enum class Operation {FILL, TOPUP}

    private val _navigateToSpecific = MutableLiveData<Boolean?>()
    val navigateToSpecific: LiveData<Boolean?>
        get() = _navigateToSpecific

    private val _navigateToFill = MutableLiveData<Boolean?>()
    val navigateToFill: LiveData<Boolean?>
        get() = _navigateToFill

    private val _operation = MutableLiveData<Operation?>()
    val operation: LiveData<Operation?>
        get() = _operation

    fun onFillClicked() {
        _navigateToFill.value = true
        _operation.value = Operation.FILL
    }

    fun onFillNavigated() {
        _navigateToFill.value = null
    }

    fun onSpecificClicked() {
        _navigateToSpecific.value = true
        _operation.value = Operation.TOPUP
    }

    fun onSpecificNavigated() {
        _navigateToSpecific.value = null
    }
}