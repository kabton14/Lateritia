package com.example.lateritia.di

import android.content.Context
import androidx.room.Room
import com.example.lateritia.database.VehicleDao
import com.example.lateritia.database.VehicleRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideVehicleDao(database: VehicleRoomDatabase): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): VehicleRoomDatabase {
        return VehicleRoomDatabase.getDatabase(appContext)
    }
}