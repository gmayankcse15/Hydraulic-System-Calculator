package com.uniquespm.hydraulic.ui.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniquespm.hydraulic.model.Cylinder
import com.uniquespm.hydraulic.model.DataRepository

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository
    private val mAllProjects: LiveData<List<Cylinder>>

    init {
        mRepository = DataRepository(application)
        mAllProjects = mRepository.getAllCylinderProjects()
    }

    fun getAllProjects() : LiveData<List<Cylinder>> =  mAllProjects

    fun insert(cylinder: Cylinder)  {
        mRepository.insert(cylinder)
    }
}