package com.example.lateritia.database

class VehicleRepository(private val vehicleDao: VehicleDao) {
    suspend fun getVehicle(id: Long = 14): Vehicle? {
        return vehicleDao.loadVehicle(id)
    }

    suspend fun updateVehicle(vehicle:Vehicle) {
        vehicleDao.update(vehicle)
    }
}
