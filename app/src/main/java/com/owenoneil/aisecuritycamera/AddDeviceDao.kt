//package com.owenoneil.aisecuritycamera
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//import androidx.room.Upsert
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface AddDeviceDao {
//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    suspend fun insertDevice(device: AddDevice)
//
//    @Query("SELECT * FROM devices")
//    suspend fun getAllDevices(): List<AddDevice>
//
//    @Query("DELETE FROM devices WHERE devicename = :name")
//    suspend fun deleteDeviceByName(name: String)
//
//    @Query("SELECT * FROM devices WHERE deviceid = :id")
//    suspend fun getDeviceById(id: Int): AddDevice?
//}
//
//@Dao
//interface AlertDao {
//    @Insert
//    suspend fun insertAlert(alert: AlertEntity): Long
//
//    @Query("SELECT * FROM alerts")
//    suspend fun getAllAlerts(): List<AlertEntity>
//
//    @Query("DELETE FROM alerts WHERE id = :id")
//    suspend fun deleteAlertById(id: Int)
//
//    @Query("SELECT * FROM alerts WHERE date(timestamp / 1000, 'unixepoch') = date(:today / 1000, 'unixepoch')")
//    suspend fun getTodaysAlerts(today: Long): List<AlertEntity>
//}
//
//@Dao
//interface HistoryAlertDao {
//    @Insert
//    suspend fun insertHistoryAlert(alert: HistoryAlertEntity): Long
//
//    @Query("SELECT * FROM history_alerts")
//    suspend fun getAllHistoryAlerts(): List<HistoryAlertEntity>
//
//    @Query("DELETE FROM history_alerts WHERE id = :id")
//    suspend fun deleteHistoryAlertById(id: Int)
//}