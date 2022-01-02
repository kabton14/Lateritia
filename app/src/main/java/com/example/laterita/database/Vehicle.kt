package com.example.laterita.database

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

    @ColumnInfo(name = "fuel_capacity")
    var fuelCapacity: Int?,

    @ColumnInfo(name = "reserve_capacity")
    var reserveCapacity: Int?
)