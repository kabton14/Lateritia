package com.example.laterita.settings

import androidx.lifecycle.*
import com.example.laterita.database.Vehicle
import com.example.laterita.database.VehicleDao
import com.example.laterita.database.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(private val id: Long,
                        private val vehicleRepository: VehicleRepository): ViewModel() {
    private var _vehicle = MutableLiveData<Vehicle?>()
    val vehicle: LiveData<Vehicle?>
        get() = _vehicle

    private var _showSnackbarEvent = MutableLiveData<Int>()
    val showSnackBarEvent: LiveData<Int>
        get() = _showSnackbarEvent

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
            val vehicle = vehicleRepository.getVehicle()
            vehicle
        }
    }

    fun editVehicle(name: String, model: String, vin: String, licence: String, divisions: Int, fuel: Double,
                    reserve: Double) {
        if (settingsAreValid(name, model, vin, licence, divisions, fuel, reserve)) {
            _vehicle.value?.make = name
            _vehicle.value?.model = model
            _vehicle.value?.vin = vin
            _vehicle.value?.licence = licence
            _vehicle.value?.divisions = divisions
            _vehicle.value?.fuelCapacity = fuel
            _vehicle.value?.reserveCapacity = reserve

            vehicle.value?.let { saveSettings(it) }
            _showSnackbarEvent.value = 1
        } else {
            _showSnackbarEvent.value = 2
        }
    }

    private fun settingsAreValid(name: String, model: String, vin: String, licence: String,
                                 divisions: Int, fuel: Double, reserve: Double): Boolean {
        return name.isNotBlank() && model.isNotBlank() && vin.isNotBlank()
                && licence.isNotBlank() && divisions >= 0 && reserve <= fuel
    }

    private fun saveSettings(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.update(vehicle)
        }
    }

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = 0
    }
}

class SettingsViewModelFactory(private val id: Long,
                               private val vehicleRepository: VehicleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(id, vehicleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}