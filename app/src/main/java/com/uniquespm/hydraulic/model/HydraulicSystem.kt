package com.uniquespm.hydraulic.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.uniquespm.hydraulic.util.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
open class HydraulicSystem(
    @NonNull
    @ColumnInfo(name = "projectName") var mProjectName: String,
    @NonNull
    @ColumnInfo(name = "projectType") var mProjectType: Int = Constants.POWERPACK
) : Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var mId: Int = 0

}