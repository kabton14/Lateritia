package com.example.lateritia.vehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lateritia.database.VehicleDao

class VehicleListViewModel(private val vehicleDao: VehicleDao): ViewModel() {
}

class VehicleListViewModelFactory(private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleListViewModel(vehicleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}