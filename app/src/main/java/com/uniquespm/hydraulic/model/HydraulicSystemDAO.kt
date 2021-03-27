package com.uniquespm.hydraulic.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HydraulicSystemDAO {
    @Insert
    fun insertCylinder(cylinder: Cylinder)

    fun insertPowerpack(powerpack: Powerpack)

    @Query("DELETE FROM cylinder_table")
    fun deleteAll();

    @Query("SELECT * FROM cylinder_table ORDER BY _id DESC")
    fun getAllHydraulicProjects(): LiveData<List<HydraulicSystem>>
}