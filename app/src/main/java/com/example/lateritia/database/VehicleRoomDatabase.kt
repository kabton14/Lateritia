package com.example.lateritia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Vehicle::class, FuelEntry::class], version = 2, exportSchema = true)
abstract class VehicleRoomDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun fuelEntryDao(): FuelEntryDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `fuel_entries` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`vehicle_model` TEXT NOT NULL, " +
                    "`lat` REAL, " +
                    "`lng` REAL, " +
                    "`price_per_liter` REAL NOT NULL, " +
                    "`total_paid` REAL NOT NULL, " +
                    "`percent_filled` INTEGER NOT NULL, " +
                    "`timestamp` INTEGER NOT NULL)"
                )
            }
        }

        @Volatile
        private var INSTANCE: VehicleRoomDatabase? = null

        fun getDatabase(context: Context): VehicleRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleRoomDatabase::class.java,
                    "vehicle_database")
                    .addMigrations(MIGRATION_1_2)
                    .createFromAsset("database/vehicle.db")
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

