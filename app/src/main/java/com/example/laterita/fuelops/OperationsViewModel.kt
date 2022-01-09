package com.example.laterita.fuelops

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.laterita.database.VehicleDao

class OperationsViewModel(private val vehicleDao: VehicleDao) : ViewModel() {
    enum class Operation {FILL, TOPUP}

    private val _navigateToPricePerLiter = MutableLiveData<Boolean?>()
    val navigateToPricePerLiter: LiveData<Boolean?>
        get() = _navigateToPricePerLiter

    private val _operation = MutableLiveData<Operation?>()
    val operation: LiveData<Operation?>
        get() = _operation

    private var _pricePerLiter: Double = 0.0
    val pricePerLiter: Double
        get() = _pricePerLiter

    private val _navigateToFuelLevel = MutableLiveData<Boolean?>()
    val navigateToFuelLevel: LiveData<Boolean?>
        get() = _navigateToFuelLevel

    private val _showSnackBarEvent = MutableLiveData<String?>()
    val showSnackbarEvent: LiveData<String?>
        get() = _showSnackBarEvent

    private val _navigateToTopupAmount = MutableLiveData<Boolean?>()
    val navigateToTopupAmount: LiveData<Boolean?>
        get() = _navigateToTopupAmount

    private val _navigateToResult = MutableLiveData<Boolean?>()
    val navigateToResult: LiveData<Boolean?>
        get() = _navigateToResult

    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun onFillOptionClicked() {
        _operation.value = Operation.FILL
        _navigateToPricePerLiter.value = true
        Log.i("OPTION", "Current Option: ${_operation.value.toString()}")
    }

    fun onTopUpOptionClicked() {
        _operation.value = Operation.TOPUP
        _navigateToPricePerLiter.value = true
        Log.i("OPTION", "Current Option: ${_operation.value.toString()}")
    }

    fun onPricePerLiterNavigated() {
        _navigateToPricePerLiter.value = null
    }

    private fun isAcceptedPrice(price: Double): Boolean {
        return price.compareTo(0.0) > 0
    }

    fun setPricePerLiter(price: Double) {
        if (isAcceptedPrice(price)) {
            _pricePerLiter = price
            navigateToFuelLevel()
        } else {
            activateSnackBarEvent("Enter a valid price")
        }
    }

    private fun navigateToFuelLevel() {
        _navigateToFuelLevel.value = true
    }

    fun onFuelLevelNavigated() {
        _navigateToFuelLevel.value = null
    }

    private fun activateSnackBarEvent(msg: String) {
        _showSnackBarEvent.value = msg
    }

    fun doneShowingSnackbar() {
        _showSnackBarEvent.value = null
    }

    fun navigateToCorrectFragmentFromFuelLevel() { //yikes
        when (_operation.value) {
            Operation.FILL -> navigateToTopUpAmount()
            else -> navigateToResult()
        }
    }

    fun navigateToTopUpAmount() {
        _navigateToTopupAmount.value = true
    }

    fun onTopUpAmountNavigated() {
        _navigateToTopupAmount.value = null
    }

    fun navigateToResult() {
        _navigateToResult.value = true
    }

    fun onResultNavigated() {
        _navigateToResult.value = null
    }

    fun navigateToHome() {
        _navigateToHome.value = true
    }

    fun onHomeNavigated() {
        _navigateToHome.value = null
    }
}

class OperationsViewModelFactory(private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OperationsViewModel(vehicleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}