package com.uniquespm.hydraulic.model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class DataRepository(application: Application) {

    var  mCylinderDAO : CylinderDAO
    var  mAllCylinderProjects :LiveData<List<Cylinder>>

    init {
        val db: CylinderRoomDatabase = CylinderRoomDatabase.getInstance(application)
        mCylinderDAO = db.cylinderDAO
        mAllCylinderProjects = mCylinderDAO.getAllCylinderProjects()
    }

    fun getAllCylinderProjects() : LiveData<List<Cylinder>> {
        return mAllCylinderProjects
    }

    fun insert(cylinder: Cylinder) {
        insertAsyncTask(mCylinderDAO).execute(cylinder)
    }

    companion object {

        class insertAsyncTask(private val mAsyncCylinderDao: CylinderDAO) : AsyncTask<Cylinder, Void, Int>() {

            override fun doInBackground(vararg params: Cylinder?): Int {
                mAsyncCylinderDao.insert(params[0]!!)
                return 0
            }

        }
    }

}