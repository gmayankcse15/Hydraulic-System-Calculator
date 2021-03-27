package com.uniquespm.hydraulic.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PowerpackDAO {
    @Insert
    fun insert(powerpack: Powerpack)

    @Query("DELETE FROM powerpack_table")
    fun deleteAll();

    @Query("SELECT * FROM powerpack_table ORDER BY _id DESC")
    fun getAllPowerpackProjects(): LiveData<List<Powerpack>>
}