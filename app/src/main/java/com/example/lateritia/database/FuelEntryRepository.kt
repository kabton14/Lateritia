package com.example.lateritia.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FuelEntryRepository @Inject constructor(private val fuelEntryDao: FuelEntryDao) {

    private val maxEntries = 100

    suspend fun insert(entry: FuelEntry) {
        withContext(Dispatchers.IO) {
            if (fuelEntryDao.getCount() >= maxEntries) {
                fuelEntryDao.deleteOldest()
            }
            fuelEntryDao.insert(entry)
        }
    }

    suspend fun getRecentEntries(): List<FuelEntry> = withContext(Dispatchers.IO) {
        fuelEntryDao.getRecentEntries()
    }

    suspend fun getAllEntries(): List<FuelEntry> = withContext(Dispatchers.IO) {
        fuelEntryDao.getAllEntries()
    }

    suspend fun deleteEntry(entry: FuelEntry) = withContext(Dispatchers.IO) {
        fuelEntryDao.deleteEntry(entry)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        fuelEntryDao.deleteAll()
    }
}
