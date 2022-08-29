package com.example.lateritia.database

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(private val vehicleDao: VehicleDao) {
    suspend fun getVehicle(id: Long = 14): Vehicle? {
        return vehicleDao.loadVehicle(id)
    }

    suspend fun updateVehicle(vehicle:Vehicle) {
        vehicleDao.update(vehicle)
    }
}
