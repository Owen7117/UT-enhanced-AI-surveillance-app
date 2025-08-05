package com.owenoneil.aisecuritycamera
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
   entities = [AddDevice::class],
    version = 1
)
abstract class DevicesDatabase: RoomDatabase() {

    abstract val dao: Any


    companion object {
        @Volatile
        private var INSTANCE: DevicesDatabase? = null

        fun getInstance(context: Context): DevicesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DevicesDatabase::class.java,
                    "devices_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}