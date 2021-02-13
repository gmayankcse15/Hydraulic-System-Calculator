package com.uniquespm.hydraulic.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cylinder::class], version = 3, exportSchema = false)
abstract class CylinderRoomDatabase : RoomDatabase() {
    abstract val cylinderDAO : CylinderDAO

    companion object {

        @Volatile
        private var INSTANCE: CylinderRoomDatabase? = null

        fun getInstance(context: Context): CylinderRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CylinderRoomDatabase::class.java,
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