package com.uniquespm.hydraulic.model

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class DataRepository(application: Context) {

    var  mCylinderDAO : CylinderDAO
    var  mAllCylinderProjects :LiveData<List<Cylinder>>

    var mPowerpackDAO: PowerpackDAO
    var mAllPowerpackProjects: LiveData<List<Powerpack>>


    init {
        val db: CommonRoomDatabase = CommonRoomDatabase.getInstance(application)
        mCylinderDAO = db.cylinderDAO
        mAllCylinderProjects = mCylinderDAO.getAllCylinderProjects()
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
        insertAsyncTaskCylinder(mCylinderDAO).execute(cylinder)
    }

    fun insertPowerpack(powerpack: Powerpack) {
        insertAsyncTaskPowerpack(mPowerpackDAO).execute(powerpack)
    }

    companion object {

        class insertAsyncTaskCylinder(private val mAsyncCylinderDao: CylinderDAO) : AsyncTask<Cylinder, Void, Int>() {
            override fun doInBackground(vararg params: Cylinder?): Int {
                mAsyncCylinderDao.insert(params[0]!!)
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