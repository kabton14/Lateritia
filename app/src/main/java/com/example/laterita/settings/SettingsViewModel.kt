package com.example.laterita.settings

import androidx.lifecycle.*
import com.example.laterita.database.Vehicle
import com.example.laterita.database.VehicleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(private val id: Long, private val vehicleDao: VehicleDao): ViewModel() {
    val database = vehicleDao

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
            val vehicle = database.loadVehicle(id)
            vehicle
        }
    }

    fun editVehicle(name: String?, model: String?, vin: String?, licence: String?, fuel: Int?,
                    reserve: Int?) {
        if (name != null) { _vehicle.value?.make = name }
        if (model != null) { _vehicle.value?.model = model }
        if (vin != null) { _vehicle.value?.vin = vin }
        if (licence != null) { _vehicle.value?.licence = licence }
        if (fuel != null) { _vehicle.value?.fuelCapacity = fuel }
        if (reserve != null) { _vehicle.value?.reserveCapacity = reserve }

        vehicle.value?.let { saveSettings(it) }
    }

    fun areSettingsValid(name: String, model: String, vin: String, licence: String, fuel: Int,
                         reserve: Int): Boolean {
        return name.isNotBlank() && model.isNotBlank() && vin.isNotBlank() && licence.isNotBlank()
                && reserve <= fuel
    }


    private fun saveSettings(vehicle: Vehicle) {
        viewModelScope.launch {
            database.update(vehicle)
        }
    }
}

class SettingsViewModelFactory(private val id: Long,
                        private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(id, vehicleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}