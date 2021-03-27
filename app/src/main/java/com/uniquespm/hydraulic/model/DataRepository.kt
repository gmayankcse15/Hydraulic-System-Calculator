package com.uniquespm.hydraulic.model

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class DataRepository(application: Context) {

    var  mHydraulicSystemDAO : HydraulicSystemDAO
    var  mAllProjects :LiveData<List<HydraulicSystem>>

    var mPowerpackDAO: PowerpackDAO
    var mAllProjectList: List <HydraulicSystem>


    init {
        val db: CommonRoomDatabase = CommonRoomDatabase.getInstance(application)
        mHydraulicSystemDAO = db.cylinderDAO
        mHydraulicSystemDAO.getAllCylinderProjects()
        mPowerpackDAO = db.powerpackDAO
        mAllPowerpackProjects = mPowerpackDAO.getAllPowerpackProjects()
    }

    fun getAllCylinderProjects() : LiveData<List<Cylinder>> {
        return mAllCylinderProjects
    }

    fun getAllPowerpackProjects() : LiveData<List<Powerpack>> {
        return mAllPowerpackProjects
    }

    fun insertCylinder(cylinder: Cylinder) {
        insertAsyncTaskCylinder(mHydraulicSystemDAO).execute(cylinder)
    }

    fun insertPowerpack(powerpack: Powerpack) {
        insertAsyncTaskPowerpack(mPowerpackDAO).execute(powerpack)
    }

    companion object {

        class insertAsyncTaskCylinder(private val mAsyncHydraulicSystemDao: HydraulicSystemDAO) : AsyncTask<Cylinder, Void, Int>() {
            override fun doInBackground(vararg params: Cylinder?): Int {
                mAsyncHydraulicSystemDao.insert(params[0]!!)
                return 0
            }
        }

        class insertAsyncTaskPowerpack(private val mAsyncPowerpackDao: PowerpackDAO) : AsyncTask<Powerpack, Void, Int>() {
            override fun doInBackground(vararg params: Powerpack?): Int {
                mAsyncPowerpackDao.insert(params[0]!!)
                return 0
            }
        }

    }

}