package com.owenoneil.aisecuritycamera

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class AddDevice(
    @PrimaryKey val deviceid: Int,
    val devicename: String
)

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "history_alerts")
data class HistoryAlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String
)