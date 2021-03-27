package com.uniquespm.hydraulic.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "powerpack_table")
class Powerpack(projectName: String, projectType: Int) : HydraulicSystem(projectName, projectType){

    @NonNull
    @ColumnInfo(name = "numCylinder") var mNumCylinder: Int = 0

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
    @ColumnInfo(name = "up_force") var mUpForce: String = ""

    @NonNull
    @ColumnInfo(name = "down_force") var mDownForce: String = ""

    @NonNull
    @ColumnInfo(name = "pressing_force") var mPressingForce: String = ""

    @NonNull
    @ColumnInfo(name = "forceUnit") var mForceUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "up_speed") var mUpSpeed: String = ""

    @NonNull
    @ColumnInfo(name = "down_speed") var mDownSpeed: String = ""

    @NonNull
    @ColumnInfo(name = "pressing_speed") var mPressingSpeed: String = ""

    @NonNull
    @ColumnInfo(name = "speedUnit") var mSpeedUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "up_pressure") var mUpPressure: String = ""

    @NonNull
    @ColumnInfo(name = "down_pressure") var mDownPressure: String = ""

    @NonNull
    @ColumnInfo(name = "pressing_pressure") var mPressingPressure: String = ""

    @NonNull
    @ColumnInfo(name = "pressureUnit") var mPressureUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "up_flow") var mUpFlow: String = ""

    @NonNull
    @ColumnInfo(name = "down_flow") var mDownFlow: String = ""

    @NonNull
    @ColumnInfo(name = "pressing_flow") var mPressingFlow: String = ""

    @NonNull
    @ColumnInfo(name = "flowUnit") var mFlowUnit: Int = 0

    @NonNull
    @ColumnInfo(name = "up_motor") var mUpMotor: String = ""

    @NonNull
    @ColumnInfo(name = "down_motor") var mDownMotor: String = ""

    @NonNull
    @ColumnInfo(name = "pressing_motor") var mPressingMotor: String = ""

    @NonNull
    @ColumnInfo(name = "motorUnit") var mMotorUnit: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
        mNumCylinder = parcel.readInt()
        mBoreDiameter = parcel.readString() ?: ""
        mBoreDiameterUnit = parcel.readInt()
        mRodDiameter = parcel.readString() ?: ""
        mRodDiameterUnit = parcel.readInt()
        mStroke = parcel.readString() ?: ""
        mStrokeUnit = parcel.readInt()
        mUpForce = parcel.readString() ?: ""
        mDownForce = parcel.readString() ?: ""
        mPressingForce = parcel.readString() ?: ""
        mForceUnit = parcel.readInt()
        mUpSpeed = parcel.readString() ?: ""
        mDownSpeed = parcel.readString() ?: ""
        mPressingSpeed = parcel.readString() ?: ""
        mSpeedUnit = parcel.readInt()
        mUpPressure = parcel.readString() ?: ""
        mDownPressure = parcel.readString() ?: ""
        mPressingPressure = parcel.readString() ?: ""
        mPressureUnit = parcel.readInt()
        mUpFlow = parcel.readString() ?: ""
        mDownFlow = parcel.readString() ?: ""
        mPressingFlow = parcel.readString() ?: ""
        mFlowUnit = parcel.readInt()
        mUpMotor = parcel.readString() ?: ""
        mDownMotor = parcel.readString() ?: ""
        mPressingMotor = parcel.readString() ?: ""
        mMotorUnit = parcel.readInt()
    }

    @Ignore
    constructor(
        projectName: String,
        projectType: Int,
        numCylinder: Int,
        boreDiameter: String,
        boreDiameterUnit: Int,
        rodDiameter: String,
        rodDiameterUnit: Int,
        stroke: String,
        strokeUnit: Int,
        upForce: String,
        downForce: String,
        pressingForce: String,
        forceUnit: Int,
        upSpeed: String,
        downSpeed: String,
        pressingSpeed: String,
        speedUnit: Int,
        upFlow: String,
        downFlow: String,
        pressingFlow: String,
        flowUnit: Int,
        upMotor: String,
        downMotor: String,
        pressingMotor: String,
        motorUnit: Int
    ) : this(projectName, projectType) {
        mProjectName = projectName
        mProjectType = projectType
        mNumCylinder = numCylinder
        mBoreDiameter = boreDiameter
        mBoreDiameterUnit = boreDiameterUnit
        mRodDiameter = rodDiameter
        mRodDiameterUnit = rodDiameterUnit
        mStroke = stroke
        mStrokeUnit = strokeUnit
        mUpForce = upForce
        mDownForce = downForce
        mPressingForce = pressingForce
        mForceUnit = forceUnit
        mUpSpeed = upSpeed
        mDownSpeed = downSpeed
        mPressingSpeed = pressingSpeed
        mSpeedUnit = speedUnit
        mUpFlow = upFlow
        mDownFlow = downFlow
        mPressingFlow = pressingFlow
        mFlowUnit = flowUnit
        mUpMotor = upMotor
        mDownMotor = downMotor
        mPressingMotor = pressingMotor
        mMotorUnit = motorUnit
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(mNumCylinder)
        parcel.writeString(mBoreDiameter)
        parcel.writeInt(mBoreDiameterUnit)
        parcel.writeString(mRodDiameter)
        parcel.writeInt(mRodDiameterUnit)
        parcel.writeString(mStroke)
        parcel.writeInt(mStrokeUnit)
        parcel.writeString(mUpForce)
        parcel.writeString(mDownForce)
        parcel.writeString(mPressingForce)
        parcel.writeInt(mForceUnit)
        parcel.writeString(mUpSpeed)
        parcel.writeString(mDownSpeed)
        parcel.writeString(mPressingSpeed)
        parcel.writeInt(mSpeedUnit)
        parcel.writeString(mUpPressure)
        parcel.writeString(mDownPressure)
        parcel.writeString(mPressingPressure)
        parcel.writeInt(mPressureUnit)
        parcel.writeString(mUpFlow)
        parcel.writeString(mDownFlow)
        parcel.writeString(mPressingFlow)
        parcel.writeInt(mFlowUnit)
        parcel.writeString(mUpMotor)
        parcel.writeString(mDownMotor)
        parcel.writeString(mPressingMotor)
        parcel.writeInt(mMotorUnit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Powerpack> {
        override fun createFromParcel(parcel: Parcel): Powerpack {
            return Powerpack(parcel)
        }

        override fun newArray(size: Int): Array<Powerpack?> {
            return arrayOfNulls(size)
        }
    }


}


