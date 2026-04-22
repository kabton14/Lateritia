package com.example.lateritia.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_entries")
data class FuelEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "vehicle_model")
    val vehicleModel: String,

    @ColumnInfo(name = "lat")
    val lat: Double?,

    @ColumnInfo(name = "lng")
    val lng: Double?,

    @ColumnInfo(name = "price_per_liter")
    val pricePerLiter: Double,

    @ColumnInfo(name = "total_paid")
    val totalPaid: Double,

    @ColumnInfo(name = "percent_filled")
    val percentFilled: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)
