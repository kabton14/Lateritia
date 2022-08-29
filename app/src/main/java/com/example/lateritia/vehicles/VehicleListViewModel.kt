package com.example.lateritia.vehicles

import androidx.lifecycle.*
import com.example.lateritia.database.Vehicle
import com.example.lateritia.database.VehicleRepository
import kotlinx.coroutines.launch

class VehicleListViewModel(private val vehicleRepository: VehicleRepository,
                           private val defaultVehicle: Long)
    : ViewModel() {

    var currentVehicle: Long = defaultVehicle
    lateinit var vehicles:LiveData<List<Vehicle>>

    private val _setDefaultVehicle = MutableLiveData<Long>()
    val setDefaultVehicle
        get() = _setDefaultVehicle

    init {
        viewModelScope.launch {
            vehicles = vehicleRepository.getAllVehicles()
        }
    }

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