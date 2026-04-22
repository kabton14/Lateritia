package com.example.lateritia.database

import androidx.room.*

@Dao
interface FuelEntryDao {

    @Insert
    fun insert(entry: FuelEntry)

    @Query("SELECT * FROM fuel_entries ORDER BY timestamp DESC LIMIT 10")
    fun getRecentEntries(): List<FuelEntry>

    @Query("SELECT * FROM fuel_entries ORDER BY timestamp DESC")
    fun getAllEntries(): List<FuelEntry>

    @Query("SELECT COUNT(*) FROM fuel_entries")
    fun getCount(): Int

    @Query("DELETE FROM fuel_entries WHERE id = (SELECT id FROM fuel_entries ORDER BY timestamp ASC LIMIT 1)")
    fun deleteOldest()
}
