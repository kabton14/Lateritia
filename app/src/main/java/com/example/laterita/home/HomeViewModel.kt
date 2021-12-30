package com.example.laterita.home

import androidx.lifecycle.*
import com.example.laterita.database.Vehicle
import com.example.laterita.database.VehicleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val vehicleDao: VehicleDao): ViewModel() {

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
            var vehicle = vehicleDao.loadVehicle(14)
            vehicle
        }
    }
}

    class HomeViewModelFactory(private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(vehicleDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


