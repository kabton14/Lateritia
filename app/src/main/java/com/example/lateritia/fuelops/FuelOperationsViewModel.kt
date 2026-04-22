package com.example.lateritia.fuelops

import android.util.Log
import androidx.lifecycle.*
import com.example.lateritia.database.FuelEntry
import com.example.lateritia.database.FuelEntryRepository
import com.example.lateritia.database.Vehicle
import com.example.lateritia.database.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.*

private const val STATION_RADIUS_METERS = 75.0

class FuelOperationsViewModel(
    private val id: Long,
    private val vehicleRepo: VehicleRepository,
    private val fuelEntryRepo: FuelEntryRepository
) : ViewModel() {
    enum class Operation {FILL, TOPUP}

    var currentVehicle = id

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

    private var _currentLat: Double? = null
    private var _currentLng: Double? = null
    private var _entrySaved = false

    private val _lastEntryAtLocation = MutableLiveData<FuelEntry?>()
    val lastEntryAtLocation: LiveData<FuelEntry?> get() = _lastEntryAtLocation

    fun setLocation(lat: Double, lng: Double) {
        _currentLat = lat
        _currentLng = lng
        viewModelScope.launch {
            val nearby = withContext(Dispatchers.IO) {
                fuelEntryRepo.getAllEntries()
                    .filter { it.lat != null && it.lng != null }
                    .firstOrNull { haversineMeters(lat, lng, it.lat!!, it.lng!!) <= STATION_RADIUS_METERS }
            }
            _lastEntryAtLocation.value = nearby
        }
    }

    fun saveEntry() {
        if (_entrySaved) return
        _entrySaved = true
        _vehicle.value?.let { v ->
            val entry = FuelEntry(
                vehicleModel = v.model ?: v.make,
                lat = _currentLat,
                lng = _currentLng,
                pricePerLiter = _pricePerLiter,
                totalPaid = _calculatedFuelCost,
                percentFilled = _calculatedFillPercentage,
                timestamp = System.currentTimeMillis()
            )
            viewModelScope.launch(Dispatchers.IO) {
                fuelEntryRepo.insert(entry)
            }
        }
    }

    private fun haversineMeters(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val R = 6_371_000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLng / 2).pow(2)
        return 2 * R * atan2(sqrt(a), sqrt(1 - a))
    }

    fun updateCurrentVehicle(id: Long) {
        currentVehicle = id
        _entrySaved = false
        initializeVehicle()
    }

    private fun initializeVehicle() {
        viewModelScope.launch {
            _vehicle.value = getVehicle(currentVehicle)
        }
    }

    private suspend fun getVehicle(id: Long): Vehicle? {
        return withContext(Dispatchers.IO) {
            var vehicle = vehicleRepo.getVehicle(id)
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
    private val id: Long,
    private val vehicleRepo: VehicleRepository,
    private val fuelEntryRepo: FuelEntryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FuelOperationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FuelOperationsViewModel(id, vehicleRepo, fuelEntryRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}