package com.uniquespm.hydraulic.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cylinder_table")
class Cylinder(projectName: String, projectType: Int) : HydraulicSystem(projectName, projectType){

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

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
        mBoreDiameter = parcel.readString() ?: ""
        mBoreDiameterUnit = parcel.readInt()
        mRodDiameter = parcel.readString() ?: ""
        mRodDiameterUnit = parcel.readInt()
        mStroke = parcel.readString() ?: ""
        mStrokeUnit = parcel.readInt()
        mPressure = parcel.readString() ?: ""
        mPressureUnit = parcel.readInt()
        mAreaBoreSide = parcel.readString() ?: ""
        mAreaRodSide = parcel.readString() ?: ""
        mAreaSideUnit = parcel.readInt()
        mVolumeBoreSide = parcel.readString() ?: ""
        mVolumeRodSide = parcel.readString() ?: ""
        mVolumeSideUnit = parcel.readInt()
        mForceBoreSide = parcel.readString() ?: ""
        mForceRodSide = parcel.readString() ?: ""
        mForceSideUnit = parcel.readInt()
    }

    @Ignore
    constructor(projectName : String,
                projectType: Int,
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
                ) : this(projectName, projectType) {
        mProjectName = projectName
        mProjectType = projectType
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(mBoreDiameter)
        parcel.writeInt(mBoreDiameterUnit)
        parcel.writeString(mRodDiameter)
        parcel.writeInt(mRodDiameterUnit)
        parcel.writeString(mStroke)
        parcel.writeInt(mStrokeUnit)
        parcel.writeString(mPressure)
        parcel.writeInt(mPressureUnit)
        parcel.writeString(mAreaBoreSide)
        parcel.writeString(mAreaRodSide)
        parcel.writeInt(mAreaSideUnit)
        parcel.writeString(mVolumeBoreSide)
        parcel.writeString(mVolumeRodSide)
        parcel.writeInt(mVolumeSideUnit)
        parcel.writeString(mForceBoreSide)
        parcel.writeString(mForceRodSide)
        parcel.writeInt(mForceSideUnit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cylinder> {
        override fun createFromParcel(parcel: Parcel): Cylinder {
            return Cylinder(parcel)
        }

        override fun newArray(size: Int): Array<Cylinder?> {
            return arrayOfNulls(size)
        }
    }
}


