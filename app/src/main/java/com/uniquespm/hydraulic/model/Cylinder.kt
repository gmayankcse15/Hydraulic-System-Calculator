package com.uniquespm.hydraulic.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cylinder_table")
class Cylinder(
    @NonNull
    @ColumnInfo(name = "projectName") var mProjectName: String) {

    @NonNull @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") var mId: Int = 0

    @NonNull
    @ColumnInfo(name = "boreDiameter") var mBoreDiameter: Double = 0.0

    @NonNull
    @ColumnInfo(name = "boreDiameterUnit") var mBoreDiameterUnit: Double = 0.0

    @NonNull
    @ColumnInfo(name = "rodDiameter") var mRodDiameter: Double = 0.0

    @NonNull
    @ColumnInfo(name = "rodDiameterUnit") var mRodDiameterUnit: Double = 0.0

    @NonNull
    @ColumnInfo(name = "stroke") var mStroke: Double = 0.0

    @NonNull
    @ColumnInfo(name = "strokeUnit") var mStrokeUnit: Double = 0.0

    @NonNull
    @ColumnInfo(name = "pressure") var mPressure: Double = 0.0

    @NonNull
    @ColumnInfo(name = "pressureUnit") var mPressureUnit: Double = 0.0

    @NonNull
    @ColumnInfo(name = "areaBoreSide") var mAreaBoreSide: Double = 0.0

    @NonNull
    @ColumnInfo(name = "areaRodSide") var mAreaRodSide: Double = 0.0

    @NonNull
    @ColumnInfo(name = "areaSideUnit") var mAreaSideUnit: Double = 0.0

    @NonNull
    @ColumnInfo(name = "volumeBoreSide") var mVolumeBoreSide: Double = 0.0

    @NonNull
    @ColumnInfo(name = "volumeRodSide") var mVolumeRodSide: Double = 0.0

    @NonNull
    @ColumnInfo(name = "volumeSideUnit") var mVolumeSideUnit: Double = 0.0

    @NonNull
    @ColumnInfo(name = "forceBoreSide") var mForceBoreSide: Double = 0.0

    @NonNull
    @ColumnInfo(name = "forceRodSide") var mForceRodSide: Double = 0.0

    @NonNull
    @ColumnInfo(name = "forceSideUnit") var mForceSideUnit: Double = 0.0
}


