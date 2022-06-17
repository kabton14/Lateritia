package com.example.lateritia.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vehicle: Vehicle)

    @Update
    fun update(vehicle: Vehicle)

    @Delete
    fun delete(vararg vehicles: Vehicle)

    @Query("select * from vehicles where id = :id")
    fun loadVehicle(id: Long): Vehicle?

    @Query("select * from vehicles ORDER BY id ASC")
    fun loadAllVehicles(): LiveData<List<Vehicle>>

}