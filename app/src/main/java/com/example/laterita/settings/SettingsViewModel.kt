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