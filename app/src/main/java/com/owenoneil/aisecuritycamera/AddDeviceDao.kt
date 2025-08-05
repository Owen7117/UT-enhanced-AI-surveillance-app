package com.owenoneil.aisecuritycamera

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AddDeviceDao {
    @Upsert
    fun insertAddDevice(devices: AddDevice)

    @Delete
    suspend fun deleteDevice(devices: AddDevice)

    @Query("SELECT * FROM devices ORDER BY deviceid DESC")
    fun getDevicesOrderedById(): Flow<List<AddDevice>>

    @Query("SELECT * FROM devices ORDER BY devicename DESC")
    fun getDevicesOrderedByName(): Flow<List<AddDevice>>
}