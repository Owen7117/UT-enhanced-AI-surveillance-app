package com.owenoneil.aisecuritycamera
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [AddDevice::class, AlertEntity::class],
    version = 2
)
abstract class DevicesDatabase : RoomDatabase() {

    abstract fun deviceDao(): AddDeviceDao
    abstract fun alertDao(): AlertDao

    companion object {
        @Volatile
        private var INSTANCE: DevicesDatabase? = null

        fun getInstance(context: Context): DevicesDatabase {
            return INSTANCE ?: synchronized(this) {


                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL(
                            "CREATE TABLE IF NOT EXISTS `alerts` (" +
                                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                    "`type` TEXT NOT NULL)"
                        )
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DevicesDatabase::class.java,
                    "devices_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
