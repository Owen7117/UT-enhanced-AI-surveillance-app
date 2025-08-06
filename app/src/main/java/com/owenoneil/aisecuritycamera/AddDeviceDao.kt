package com.owenoneil.aisecuritycamera

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AddDeviceDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDevice(device: AddDevice)

    @Query("SELECT * FROM devices")
    suspend fun getAllDevices(): List<AddDevice>

    @Query("SELECT * FROM devices WHERE deviceid = :id")
    suspend fun getDeviceById(id: Int): AddDevice?
}