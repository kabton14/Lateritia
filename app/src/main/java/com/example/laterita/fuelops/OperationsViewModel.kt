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

    private val _pricePerLiter = MutableLiveData<Double>(0.0)
    val pricePerLiter: LiveData<Double>
        get() = _pricePerLiter

    private val _navigateToFuelLevel = MutableLiveData<Boolean?>()
    val navigateToFuelLevel: LiveData<Boolean?>
        get() = _navigateToFuelLevel

    private val _showSnackBarEvent = MutableLiveData<String?>()
    val showSnackbarEvent: LiveData<String?>
        get() = _showSnackBarEvent


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

    private fun isAcceptedPrice(price: Double): Boolean {
        return price.compareTo(0.0) > 0
    }

    fun setPricePerLiter(price: Double) {
        if (isAcceptedPrice(price)) {
            _pricePerLiter.value = price
        }
    }

    fun onFuelLevelClicked() {
        _navigateToFuelLevel.value = true
    }

    fun onFuelLevelNavigated() {
        _navigateToFuelLevel.value = null
    }
}