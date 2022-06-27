package com.example.lateritia.home

import androidx.lifecycle.*
import com.example.lateritia.database.Vehicle
import com.example.lateritia.database.VehicleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val vehicleDao: VehicleDao, private val default: Long): ViewModel() {

    var currentVehicle = default

    private var _vehicle = MutableLiveData<Vehicle?>()
    val vehicle: LiveData<Vehicle?>
        get() = _vehicle

    private val _navigateToVehicles = MutableLiveData<Boolean?>()
    val navigateToVehicles: MutableLiveData<Boolean?>
        get() = _navigateToVehicles

    private val _navigateToSettings = MutableLiveData<Long?>()
    val navigateToSettings: MutableLiveData<Long?>
        get() = _navigateToSettings

    private val _navigateToOperations = MutableLiveData<Long?>()
    val navigateToOperations: MutableLiveData<Long?>
        get() = _navigateToOperations

    fun onVehiclesClicked() {
        _navigateToVehicles.value = true
    }

    fun onVehiclesNavigated() {
        _navigateToVehicles.value = null
    }

    fun onSettingsClicked() {
        _navigateToSettings.value = vehicle.value?.id
    }

    fun onSettingsNavigated() {
        _navigateToSettings.value = null
    }

    fun onOperationsClicked() {
        _navigateToOperations.value = currentVehicle
    }

    fun onOperationsNavigated() {
        _navigateToOperations.value = null
    }

    init {
        initializeVehicle()
    }

    fun updateVehicle(id: Long) {
        currentVehicle = id
        initializeVehicle()
    }

    private fun initializeVehicle() {
        viewModelScope.launch {
            _vehicle.value = getVehicle()
        }
    }

    private suspend fun getVehicle(): Vehicle? {
        return withContext(Dispatchers.IO) {
            var vehicle = vehicleDao.loadVehicle(currentVehicle)
            vehicle
        }
    }
}

class HomeViewModelFactory(private val vehicleDao: VehicleDao, private val default: Long)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(vehicleDao, default) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


