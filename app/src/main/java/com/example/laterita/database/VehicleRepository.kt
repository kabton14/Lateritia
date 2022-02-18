package com.example.laterita.database

class VehicleRepository(private val vehicleDao: VehicleDao) {
    suspend fun getVehicle(id: Long): Vehicle? {
        return vehicleDao.loadVehicle(id)
    }
}
