package com.example.laterita.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.laterita.database.VehicleDao

class HomeViewModel(private val vehicleDao: VehicleDao): ViewModel() {
}

class HomeViewModelFactory(private val vehicleDao: VehicleDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not yet implemented")
    }
}
