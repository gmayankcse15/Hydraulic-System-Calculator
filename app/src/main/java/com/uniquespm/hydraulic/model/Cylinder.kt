package com.uniquespm.hydraulic.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.uniquespm.hydraulic.util.*
import com.uniquespm.hydraulic.util.Constants.Companion.CYLINDER
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cylinder_table")
@Parcelize
class Cylinder(
    @NonNull
    @ColumnInfo(name = "projectName") var mProjectName: String) : Parcelable{

    @NonNull @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var mId: Int = 0

    @NonNull
    @ColumnInfo(name = "projectType") var mProjectType: Int = CYLINDER

    @NonNull
    @ColumnInfo(name = "boreDiameter") var mBoreDiameter: String = ""

    @NonNull
    @ColumnInfo(name = "boreDiameterUnit") var mBoreDiameterUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "rodDiameter") var mRodDiameter: String = ""

    @NonNull
    @ColumnInfo(name = "rodDiameterUnit") var mRodDiameterUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "stroke") var mStroke: String = ""

    @NonNull
    @ColumnInfo(name = "strokeUnit") var mStrokeUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "pressure") var mPressure: String = ""

    @NonNull
    @ColumnInfo(name = "pressureUnit") var mPressureUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "areaBoreSide") var mAreaBoreSide: String = ""

    @NonNull
    @ColumnInfo(name = "areaRodSide") var mAreaRodSide: String = ""

    @NonNull
    @ColumnInfo(name = "areaSideUnit") var mAreaSideUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "volumeBoreSide") var mVolumeBoreSide: String = ""

    @NonNull
    @ColumnInfo(name = "volumeRodSide") var mVolumeRodSide: String = ""

    @NonNull
    @ColumnInfo(name = "volumeSideUnit") var mVolumeSideUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "forceBoreSide") var mForceBoreSide: String = ""

    @NonNull
    @ColumnInfo(name = "forceRodSide") var mForceRodSide: String = ""

    @NonNull
    @ColumnInfo(name = "forceSideUnit") var mForceSideUnit: Int = 0

    @Ignore
    constructor(projectName : String,
                boreDiameter: String,
                boreDiameterUnit : Int,
                rodDiameter: String,
                rodDiameterUnit: Int,
                stroke: String,
                strokeUnit: Int,
                pressure: String,
                pressureUnit: Int,
                areaBoreSide: String,
                areaRodSide: String,
                areaSideUnit: Int,
                volumeBoreSide: String,
                volumeRodSide: String,
                volumeSideUnit: Int,
                forceBoreSide: String,
                forceRodSide: String,
                forceSideUnit: Int
                ) : this(projectName) {
        mProjectName = projectName
        mBoreDiameter = boreDiameter
        mBoreDiameterUnit = boreDiameterUnit
        mRodDiameter = rodDiameter
        mRodDiameterUnit = rodDiameterUnit
        mStroke = stroke
        mStrokeUnit = strokeUnit
        mPressure = pressure
        mPressureUnit = pressureUnit
        mAreaBoreSide = areaBoreSide
        mAreaRodSide = areaRodSide
        mAreaSideUnit = areaSideUnit
        mVolumeBoreSide = volumeBoreSide
        mVolumeRodSide = volumeRodSide
        mVolumeSideUnit = volumeSideUnit
        mForceBoreSide = forceBoreSide
        mForceRodSide = forceRodSide
        mForceSideUnit = forceSideUnit
    }
}


