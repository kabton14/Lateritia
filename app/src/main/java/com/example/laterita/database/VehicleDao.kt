package com.example.laterita.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vehicle: Vehicle)

    @Update
    suspend fun update(vehicle: Vehicle)

    @Delete
    suspend fun delete(vararg vehicles: Vehicle)

    @Query("select * from vehicles where id = :id")
    suspend fun loadVehicle(id: Int): Vehicle?

    @Query("select * from vehicles ORDER BY id ASC")
    suspend fun loadAllVehicles(): LiveData<List<Vehicle>>

}