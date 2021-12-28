package com.example.laterita.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "make")
    val make: String,

    @ColumnInfo(name = "model")
    val model: String,

    @ColumnInfo(name = "vin")
    val vin: String,

    @ColumnInfo(name = "licence")
    val licence: String,

    @ColumnInfo(name = "fuel_capacity")
    val fuelCapacity: Int,

    @ColumnInfo(name = "reserve_capacity")
    val reserveCapacity: Int = 6
)