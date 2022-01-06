package com.example.laterita.fuelops

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OperationsViewModel : ViewModel() {
    private val _navigateToSpecific = MutableLiveData<Boolean?>()
    val navigateToSpecific: MutableLiveData<Boolean?>
        get() = _navigateToSpecific

    private val _navigateToFill = MutableLiveData<Boolean?>()
    val navigateToFill: MutableLiveData<Boolean?>
        get() = _navigateToFill

    fun onFillClicked() {
        _navigateToFill.value = true
    }

    fun onFillNavigated() {
        _navigateToFill.value = null
    }

    fun onSpecificClicked() {
        _navigateToSpecific.value = true
    }

    fun onSpecificNavigated() {
        _navigateToSpecific.value = null
    }
}