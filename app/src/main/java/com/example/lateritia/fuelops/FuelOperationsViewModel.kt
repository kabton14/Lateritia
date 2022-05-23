package com.example.lateritia.fuelops

import android.util.Log
import androidx.lifecycle.*
import com.example.lateritia.database.Vehicle
import com.example.lateritia.database.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class FuelOperationsViewModel(private val vehicleRepo: VehicleRepository) : ViewModel() {
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
            var vehicle = vehicleRepo.getVehicle()
            vehicle
        }
    }

    fun onFillOptionClicked() {
        _operation.value = Operation.FILL
        navigateToPricePerLiter()
        Log.i("OPTION", "Current Option: ${_operation.value.toString()}")
    }

    fun onTopUpOptionClicked() {
        _operation.value = Operation.TOPUP
        navigateToPricePerLiter()
        Log.i("OPTION", "Current Option: ${_operation.value.toString()}")
    }

    fun setPricePerLiter(price: Double) {
        if (isAcceptedPrice(price)) {
            _pricePerLiter = price
            navigateToFuelLevel()
        } else {
            activateSnackBarEvent("Enter a valid price")
        }
    }

    private fun isAcceptedPrice(price: Double): Boolean {
        return price.compareTo(0.0) > 0
    }

    fun setFuelLevel(level: Int) {
        _fuelLevel = level
    }

    fun setTopUpAmount(amount: Double) {
        _spendAmount = amount
    }

    private fun navigateToPricePerLiter() {
        _navigateToPricePerLiter.value = true
    }

    fun onPricePerLiterNavigated() {
        _navigateToPricePerLiter.value = null
    }

    private fun navigateToFuelLevel() {
        _navigateToFuelLevel.value = true
    }

    fun onFuelLevelNavigated() {
        _navigateToFuelLevel.value = null
    }

    fun navigateToCorrectFragmentFromFuelLevel() {
        when (_operation.value) {
            Operation.TOPUP -> navigateToTopUpAmount()
            else -> setCalculatedValuesAndNavigateToResult()
        }
    }

    private fun navigateToTopUpAmount() {
        _navigateToTopupAmount.value = true
    }

    fun onTopUpAmountNavigated() {
        _navigateToTopupAmount.value = null
    }

    fun setCalculatedValuesAndNavigateToResult() {
        _vehicle.value?.let {
            _calculatedFuelCost = roundToNext100(calculateFuelCost(it, _fuelLevel, _pricePerLiter))
            _calculatedFillPercentage = 100
            _calculatedBars = it.divisions
            if (_operation.value == Operation.TOPUP && _spendAmount < _calculatedFuelCost) {
                _calculatedFuelCost = spendAmount
                _calculatedFillPercentage = calculatePercentYielded(it, _pricePerLiter,
                    _fuelLevel, _calculatedFuelCost)
                _calculatedBars = calculateBarsYielded(it, _pricePerLiter,
                    _fuelLevel, _calculatedFuelCost)
            }
            navigateToResult()
        }
    }

    private fun navigateToResult() {
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

    private fun activateSnackBarEvent(msg: String) {
        _showSnackBarEvent.value = msg
    }

    fun doneShowingSnackbar() {
        _showSnackBarEvent.value = null
    }

    private fun calculateFuelCost(vehicle: Vehicle, currentBars: Int,
                                  pricePerLiter: Double) : Double {
        val totalDivisions = vehicle.divisions
        val litersPerDivision = (vehicle.fuelCapacity - vehicle.reserveCapacity) / totalDivisions
        val barsRequired = totalDivisions - currentBars
        return litersPerDivision * barsRequired * pricePerLiter
    }

    private fun calculateBarsYielded(vehicle: Vehicle, pricePerLiter: Double,
                                     currentFuelLevel: Int, totalCost: Double): Int {
        val totalDivisions = vehicle.divisions
        val litersPerDivision = (vehicle.fuelCapacity - vehicle.reserveCapacity) / totalDivisions
        val litersYielded = totalCost / pricePerLiter
        return (litersYielded / litersPerDivision).roundToInt() + currentFuelLevel
    }

    private fun calculatePercentYielded(vehicle: Vehicle, pricePerLiter: Double,
                                        currentFuelLevel: Int, totalCost: Double): Int {
        val totalLiters = (vehicle.fuelCapacity - vehicle.reserveCapacity)
        val litersPerBar = totalLiters / vehicle.divisions
        val currentLiters = currentFuelLevel * litersPerBar
        val litersYielded = totalCost / pricePerLiter
        return (((litersYielded + currentLiters) / totalLiters) * 100).roundToInt()
    }

    private fun roundToNext100(amount: Double): Double {
        var result: Double = amount
        if ((amount % 100).compareTo(0.0) != 0) {
            result  = 100 - amount % 100 + amount
        }
        return result
    }
}

class FuelOperationsViewModelFactory(
    private val vehicleRepo: VehicleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuelOperationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FuelOperationsViewModel(vehicleRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}