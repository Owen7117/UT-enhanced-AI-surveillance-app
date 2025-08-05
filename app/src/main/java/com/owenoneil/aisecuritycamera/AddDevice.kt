package com.owenoneil.aisecuritycamera

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class AddDevice(
    val devicename: String,
    val deviceid: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)