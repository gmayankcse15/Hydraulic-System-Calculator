package com.uniquespm.hydraulic.ui.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniquespm.hydraulic.model.Cylinder
import com.uniquespm.hydraulic.model.DataRepository
import com.uniquespm.hydraulic.model.HydraulicSystem

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository
    private val mAllProjects: LiveData<List<HydraulicSystem>>

    init {
        mRepository = DataRepository(application)
        mAllProjects = mRepository.getAllCylinderProjects()
    }

    fun getAllProjects() : LiveData<List<Cylinder>> =  mAllProjects

    fun insert(cylinder: Cylinder)  {
        mRepository.insertCylinder(cylinder)
    }
}