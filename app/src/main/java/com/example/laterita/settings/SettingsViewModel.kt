package com.example.laterita.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.laterita.database.VehicleDao


class SettingsViewModel(private val id: Int,
                        private val vehicleDao: VehicleDao): ViewModel() {

}

class SettingsViewModel(private val id: Int,
                        private val vehicleDao: VehicleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(vehicleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}