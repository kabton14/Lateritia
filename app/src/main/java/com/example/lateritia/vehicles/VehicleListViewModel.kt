package com.example.lateritia.vehicles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lateritia.database.VehicleDao
import com.example.lateritia.database.VehicleRepository

class VehicleListViewModel(private val vehicleDao: VehicleDao, private val defaultVehicle: Long)
    : ViewModel() {

    var currentVehicle: Long = defaultVehicle

    val vehicles = vehicleDao.loadAllVehicles()

    private val _setDefaultVehicle = MutableLiveData<Long>()
    val setDefaultVehicle
        get() = _setDefaultVehicle

    fun onDefaultIndicatorClicked(id: Long) {
        _setDefaultVehicle.value = id
    }

    fun onDefaultVehicleSet() {
        _setDefaultVehicle.value?.let {
            currentVehicle = it
        }
        _setDefaultVehicle.value = null
    }
}

class VehicleListViewModelFactory(private val vehicleRepository: VehicleRepository, private val defaultVehicle: Long)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleListViewModel(vehicleRepository, defaultVehicle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}