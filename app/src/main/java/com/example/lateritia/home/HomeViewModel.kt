package com.example.lateritia.home

import androidx.lifecycle.*
import com.example.lateritia.database.Vehicle
import com.example.lateritia.database.VehicleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val vehicleDao: VehicleDao): ViewModel() {

    private var _vehicle = MutableLiveData<Vehicle?>()
    val vehicle: LiveData<Vehicle?>
        get() = _vehicle

    private val _navigateToSettings = MutableLiveData<Long?>()
    val navigateToSettings: MutableLiveData<Long?>
        get() = _navigateToSettings

    private val _navigateToOperations = MutableLiveData<Boolean?>()
    val navigateToOperations: MutableLiveData<Boolean?>
        get() = _navigateToOperations

    fun onSettingsClicked() {
        _navigateToSettings.value = vehicle.value?.id
    }

    fun onSettingsNavigated() {
        _navigateToSettings.value = null
    }

    fun onOperationsClicked() {
        _navigateToOperations.value = true
    }

    fun onOperationsNavigated() {
        _navigateToOperations.value = null
    }

    init {
        initializeVehicle()
    }

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
}

class HomeViewModelFactory(private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(vehicleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


