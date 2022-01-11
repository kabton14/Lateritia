package com.example.laterita.fuelops

import android.util.Log
import androidx.lifecycle.*
import com.example.laterita.database.Vehicle
import com.example.laterita.database.VehicleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FuelOperationsViewModel(private val vehicleDao: VehicleDao) : ViewModel() {
    enum class Operation {FILL, TOPUP}

    init {
        initializeVehicle()
    }

    private var _vehicle = MutableLiveData<Vehicle?>()
    val vehicle: LiveData<Vehicle?>
        get() = _vehicle

    private val _operation = MutableLiveData<Operation?>()
    val operation: LiveData<Operation?>
        get() = _operation

    private var _pricePerLiter: Double = 0.0
    val pricePerLiter: Double
        get() = _pricePerLiter

    private var _fuelLevel: Int = 0
    val fuelLevel: Int
        get() = _fuelLevel

    private var _calculatedFuelCost: Double = 0.0
    val calculatedFuelCost: Double
        get() = _calculatedFuelCost

    private val _navigateToPricePerLiter = MutableLiveData<Boolean?>()
    val navigateToPricePerLiter: LiveData<Boolean?>
        get() = _navigateToPricePerLiter

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

    private fun initializeVehicle() {
        viewModelScope.launch {
            _vehicle.value = getVehicle()
        }
    }

    private suspend fun getVehicle(): Vehicle? {
        return withContext(Dispatchers.IO) {
            var vehicle = vehicleDao.loadVehicle(14)
            vehicle
        }
    }

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

    fun navigateToCorrectFragmentFromFuelLevel() {
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

    fun calculateFuelCost(vehicle: Vehicle, currentBars: Int, pricePerLiter: Double) : Double {
        val totalDivisions = 8
        val litersPerDivision = (vehicle.fuelCapacity - vehicle.reserveCapacity) / totalDivisions
        val barsRequired = totalDivisions - currentBars
        return roundToNext100(litersPerDivision * barsRequired * pricePerLiter)
    }

    private fun roundToNext100(amount: Double): Double {
        return ((amount + 99) / 100) * 100 //Takes advantage of integer division
    }
}

class FuelOperationsViewModelFactory(private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuelOperationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FuelOperationsViewModel(vehicleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}