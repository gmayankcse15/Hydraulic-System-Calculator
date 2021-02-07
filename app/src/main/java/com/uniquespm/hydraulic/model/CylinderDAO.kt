package com.uniquespm.hydraulic.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CylinderDAO {
    @Insert
    fun insert(cylinder: Cylinder)

    @Query("DELETE FROM cylinder_table")
    fun deleteAll();

    @Query("SELECT * FROM cylinder_table ORDER BY _id DESC")
    fun getAllCylinderProjects(): LiveData<List<Cylinder>>
}