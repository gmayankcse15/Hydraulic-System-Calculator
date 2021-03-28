package com.uniquespm.hydraulic.ui.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.uniquespm.hydraulic.model.Cylinder
import com.uniquespm.hydraulic.model.DataRepository
import com.uniquespm.hydraulic.model.HydraulicSystem
import com.uniquespm.hydraulic.model.Powerpack

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository
    private val mPowerpackProjectList: LiveData<List<Powerpack>>
    private val mCylinderProjectList : LiveData<List<Cylinder>>

    init {
        mRepository = DataRepository(application)
        mCylinderProjectList = mRepository.getAllCylinderProjects()
        mPowerpackProjectList = mRepository.getAllPowerpackProjects()
    }

    fun getCylinderProjects() : LiveData<List<Cylinder>> =  mCylinderProjectList

    fun getPowerpackProjects() : LiveData<List<Powerpack>> = mPowerpackProjectList

    fun insert(cylinder: Cylinder)  {
        mRepository.insertCylinder(cylinder)
    }
}