package com.uniquespm.hydraulic.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Cylinder::class, Powerpack::class), version = 4, exportSchema = false)
abstract class CommonRoomDatabase : RoomDatabase() {
    abstract val cylinderDAO : CylinderDAO
    abstract val powerpackDAO: PowerpackDAO
    companion object {

        @Volatile
        private var INSTANCE: CommonRoomDatabase? = null

        fun getInstance(context: Context): CommonRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommonRoomDatabase::class.java,
                        "cylinder_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}