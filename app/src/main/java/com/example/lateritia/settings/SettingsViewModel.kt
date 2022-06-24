package com.example.lateritia.settings

import androidx.lifecycle.*
import com.example.lateritia.database.Vehicle
import com.example.lateritia.database.VehicleRepository
import kotlinx.coroutines.CoroutineScope
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
            _vehicle.value = getVehicle(id)
        }
    }

    private suspend fun getVehicle(id: Long): Vehicle? {
        return withContext(Dispatchers.IO) {
            val vehicle = vehicleRepository.getVehicle(id)
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
        CoroutineScope(Dispatchers.IO).launch {
            vehicleRepository.updateVehicle(vehicle)
        }
    }

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
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