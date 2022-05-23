package com.example.lateritia.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "make")
    var make: String,

    @ColumnInfo(name = "model")
    var model: String?,

    @ColumnInfo(name = "vin")
    var vin: String?,

    @ColumnInfo(name = "licence")
    var licence: String?,

    @ColumnInfo(name = "divisions", defaultValue = "8")
    var divisions: Int,

    @ColumnInfo(name = "fuel_capacity", defaultValue = "45.0")
    var fuelCapacity: Double,

    @ColumnInfo(name = "reserve_capacity", defaultValue = "8.5")
    var reserveCapacity: Double
)