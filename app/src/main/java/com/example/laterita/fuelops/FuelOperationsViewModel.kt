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

    private var _vehicle = MutableLiveData<Vehicle?>()
    val vehicle: LiveData<Vehicle?>
        get() = _vehicle

    init {
        initializeVehicle()
    }

    private val _operation = MutableLiveData<Operation?>()
    val operation: LiveData<Operation?>
        get() = _operation

    private var _pricePerLiter: Double = 0.0
    val pricePerLiter: Double
        get() = _pricePerLiter

    private var _fuelLevel: Int = 0
    val fuelLevel: Int
        get() = _fuelLevel

    private var _spendAmount: Double = 0.0
    val spendAmount: Double
        get() = _spendAmount

    private var _calculatedFuelCost: Double = 0.0
    val calculatedFuelCost: Double
        get() = _calculatedFuelCost

    private var _calculatedBars: Int = 0
    val calculatedBars: Int
        get() = _calculatedBars

    private var _calculatedFillPercentage: Int = 0
    val calculatedFillPercentage: Int
        get() = _calculatedFillPercentage

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

    fun setFuelLevel(level: Int) {
        _fuelLevel = level
    }

    fun setTopUpAmount(amount: Double) {
        _spendAmount = amount
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
            Operation.TOPUP -> navigateToTopUpAmount()
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
        _vehicle.value?.let {
            _calculatedFuelCost = calculateFuelCost(it, _fuelLevel, _pricePerLiter)
            if (_operation.value == Operation.TOPUP && _spendAmount < _calculatedFuelCost) {
                _calculatedFuelCost = spendAmount
            }
            _navigateToResult.value = true
        }
        Log.i("LEVEL", "Fuel Level: ${_fuelLevel.toString()}")
        Log.i("COST", "Fuel Cost: ${_calculatedFuelCost.toString()}")
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

    private fun calculateFuelCost(vehicle: Vehicle, currentBars: Int, pricePerLiter: Double) : Double {
        val totalDivisions = vehicle.divisions
        val litersPerDivision = (vehicle.fuelCapacity - vehicle.reserveCapacity) / totalDivisions
        val barsRequired = totalDivisions - currentBars
        return roundToNext100(litersPerDivision * barsRequired * pricePerLiter)
    }

    private fun roundToNext100(amount: Double): Double {
        var result: Double = amount
        if ((amount % 100).compareTo(0.0) != 0) {
            result  = 100 - amount % 100 + amount
        }
        return result
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