package com.example.lateritia.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: Vehicle)

    @Update
    suspend fun update(vehicle: Vehicle)

    @Delete
    suspend fun delete(vararg vehicles: Vehicle)

    @Query("select * from vehicles where id = :id")
    suspend fun loadVehicle(id: Long): Vehicle?

    @Query("select * from vehicles ORDER BY id ASC")
    fun loadAllVehicles(): LiveData<List<Vehicle>?>

}