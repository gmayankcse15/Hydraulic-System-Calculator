package com.uniquespm.hydraulic.util

import android.util.Log
import android.widget.EditText
import androidx.loader.content.Loader
import java.text.DecimalFormat
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.pow

class FormulaUtil {

    companion object {

        const val EFFICIENCY = 0.85

        const val TAG = "FormulaUtil"

        fun isValid(boreDiameter: String): Boolean {
            return (boreDiameter.isNotEmpty() && boreDiameter != ".")
        }

        fun getValidData(editText: EditText, inputSet: MutableSet<EditText>?): String {
            return if (isValid(editText.text.toString()) && inputSet?.contains(editText) == true) {
                editText.text.toString()
            } else {
                ""
            }
        }

        private val decimalFormat: DecimalFormat = DecimalFormat("#.##")

        fun calculateForceBoreSide(
            boreDiameter: String,
            boreUnit: UNIT,
            rodDiameter: String,
            rodUnit: UNIT,
            pressure: String,
            pressureUnit: UNIT,
            forceRodSide: String,
            forceUnit: UNIT,
            toForceUnit: UNIT
        ): String {
            if (isValid(boreDiameter) && (isValid(pressure) || (isValid(rodDiameter) && isValid(
                    forceRodSide
                )))
            ) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                return if (isValid(boreDiameter) && isValid(pressure)) {
                    var pR = pressure.toDouble()
                    pR = ConversionUtil.convertUnit(pR, pressureUnit, PRESSURE.BAR)
                    var res: Double = (PI * bD.pow(2.0) * pR * 10.0) / 4.0
                    res = ConversionUtil.convertUnit(res, FORCE.NEWTON, toForceUnit)
                    decimalFormat.format(res).toString()
                } else {
                    var rD = rodDiameter.toDouble()
                    rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.CM)
                    var fR = forceRodSide.toDouble()
                    fR = ConversionUtil.convertUnit(fR, forceUnit, FORCE.NEWTON)
                    var res = (bD.pow(2.0) * fR) / (bD.pow(2.0) - rD.pow(2.0))
                    res = ConversionUtil.convertUnit(res, FORCE.NEWTON, toForceUnit)
                    decimalFormat.format(res).toString()
                }
            }
            return ""
        }

        fun calculateForceRodSide(
            boreDiameter: String,
            boreUnit: UNIT,
            rodDiameter: String,
            rodUnit: UNIT,
            pressure: String,
            pressureUnit: UNIT,
            forceBoreSide: String,
            forceUnit: UNIT,
            toForceUnit: UNIT
        ): String {
            if (isValid(boreDiameter) && isValid(rodDiameter) && ((isValid(pressure)) || isValid(
                    forceBoreSide
                ))
            ) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var rD = rodDiameter.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.CM)
                return if (isValid(boreDiameter) && isValid(rodDiameter) && isValid(pressure)) {
                    var pR = pressure.toDouble()
                    pR = ConversionUtil.convertUnit(pR, pressureUnit, PRESSURE.BAR)
                    var res = (PI * (bD.pow(2.0) - (rD.pow(2.0))) * pR * 10.0) / 4.0
                    res = ConversionUtil.convertUnit(res, FORCE.NEWTON, toForceUnit)
                    decimalFormat.format(res).toString()
                } else {
                    var fB = forceBoreSide.toDouble()
                    fB = ConversionUtil.convertUnit(fB, forceUnit, FORCE.NEWTON)
                    var res = ((bD.pow(2) - rD.pow(2)) * fB) / bD.pow(2.0)
                    res = ConversionUtil.convertUnit(res, FORCE.NEWTON, toForceUnit)
                    decimalFormat.format(res).toString()
                }
            }
            return ""
        }

        fun calculatePressure(
            boreDiameter: String,
            boreUnit: UNIT,
            rodDiameter: String,
            rodUnit: UNIT,
            forceRodSide: String,
            forceBoreSide: String,
            forceUnit: UNIT,
            toPressureUnit: UNIT
        ): String {
            if (isValid(boreDiameter) && (isValid(forceBoreSide) || (isValid(rodDiameter) && isValid(
                    forceRodSide
                )))
            ) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                return if (isValid(boreDiameter) && isValid(forceBoreSide)) {
                    var fB = forceBoreSide.toDouble()
                    fB = ConversionUtil.convertUnit(fB, forceUnit, FORCE.TON)
                    var res = (4.0 * fB) / (PI * bD.pow(2.0) * 10.0)
                    res = ConversionUtil.convertUnit(res, PRESSURE.BAR, toPressureUnit)
                    decimalFormat.format(res).toString()
                } else {
                    var rD = rodDiameter.toDouble()
                    rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.CM)
                    var fR = forceRodSide.toDouble()
                    fR = ConversionUtil.convertUnit(fR, forceUnit, FORCE.TON)
                    var res = (4.0 * fR) / (PI * (bD.pow(2.0) - rD.pow(2.0)) * 10)
                    res = ConversionUtil.convertUnit(res, PRESSURE.BAR, toPressureUnit)
                    decimalFormat.format(res).toString()
                }
            }
            return ""
        }

        fun calculateAreaBoreSide(
            boreDiameter: String,
            boreUnit: UNIT,
            toAreaUnit: UNIT
        ): String {
            if (isValid(boreDiameter)) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.MM)
                var res = (PI * bD.pow(2.0)) / 4.0
                res = ConversionUtil.convertUnit(res, AREA.MM2, toAreaUnit)
                Log.d(TAG, "Area BoreSide: $res")
                return decimalFormat.format(res).toString()
            }
            return ""
        }

        fun calculateAreaRodSide(
            boreDiameter: String,
            boreUnit: UNIT,
            rodDiameter: String,
            rodUnit: UNIT,
            toAreaUnit: UNIT
        ): String {
            if (isValid(boreDiameter) && isValid(rodDiameter)) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.MM)
                var rD = rodDiameter.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.MM)
                var res = (PI * (bD.pow(2.0) - rD.pow(2.0))) / 4.0
                res = ConversionUtil.convertUnit(res, AREA.MM2, toAreaUnit)
                return decimalFormat.format(res).toString()
            }
            return ""
        }

        fun calculateVolumeBoreSide(
            boreDiameter: String,
            boreUnit: UNIT,
            stroke: String,
            strokeUnit: UNIT,
            toVolumeUnit: UNIT
        ): String {
            if (isValid(boreDiameter) && isValid(stroke)) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.MM)
                var str = stroke.toDouble()
                str = ConversionUtil.convertUnit(str, strokeUnit, LENGTH.MM)
                var res = (PI * bD.pow(2.0) * str) / 4.0
                res = ConversionUtil.convertUnit(res, VOLUME.MM3, toVolumeUnit)
                return decimalFormat.format(res).toString()
            }
            return ""
        }

        fun calculateVolumeRodSide(
            boreDiameter: String,
            boreUnit: UNIT,
            rodDiameter: String,
            rodUnit: UNIT,
            stroke: String,
            strokeUnit: UNIT,
            toVolumeUnit: UNIT
        ): String {
            if (isValid(boreDiameter) && isValid(stroke) && isValid(rodDiameter)) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.MM)
                var rD = rodDiameter.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.MM)
                var str = stroke.toDouble()
                str = ConversionUtil.convertUnit(str, strokeUnit, LENGTH.MM)
                var res = (PI * (bD.pow(2.0) - rD.pow(2.0)) * str) / 4.0
                res = ConversionUtil.convertUnit(res, VOLUME.MM3, toVolumeUnit)
                return decimalFormat.format(res).toString()
            }
            return ""
        }

        fun calculatePowerpackForceSide(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            rodString: String,
            rodUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            forceUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && isValid(rodString) && (isValid(pressureString) || (isValid(motorString) && (isValid(flowString)||isValid(speedString))))) {
                var force = 0.0
                var nCylinder = numCylinder.toInt()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var bR = rodString.toDouble()
                bR = ConversionUtil.convertUnit(bR, rodUnit, LENGTH.CM)

                var pressure = 0.0
                if (isValid(pressureString)) {
                    pressure = pressureString.toDouble()
                    pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var flow = 0.0
                    if (isValid(flowString)) {
                        flow = flowString.toDouble()
                        flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                    } else if (isValid(speedString)) {
                        var speed = speedString.toDouble()
                        speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                        flow =
                            (speed * PI * (bD.pow(2) - bR.pow(2)) * nCylinder) / 40.0
                    }
                    pressure = (motor * 600) / flow
                }
                force = nCylinder * PI / 4.0 * (bD.pow(2) - bR.pow(2)) * pressure * 10
                force = ConversionUtil.convertUnit(force, FORCE.NEWTON, forceUnit)
                return decimalFormat.format(force).toString()
            }
            return "";
        }

        fun calculatePowerpackForceSide(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            forceUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && (isValid(pressureString) || (isValid(motorString) && (isValid(flowString)||isValid(speedString))))) {
                var force = 0.0
                var nCylinder = numCylinder.toInt()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)

                var pressure = 0.0
                if (isValid(pressureString)) {
                    pressure = pressureString.toDouble()
                    pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var flow = 0.0
                    if (isValid(flowString)) {
                        flow = flowString.toDouble()
                        flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                    } else if (isValid(speedString)) {
                        var speed = speedString.toDouble()
                        speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                        flow =
                            (speed * PI * bD.pow(2) * nCylinder) / 40.0
                    }
                    pressure = (motor * 600) / flow
                }
                force = nCylinder * PI / 4.0 * bD.pow(2) * pressure * 10
                force = ConversionUtil.convertUnit(force, FORCE.NEWTON, forceUnit)
                return decimalFormat.format(force).toString()
            }
            return ""
        }

        fun calculatePowerpackPressureSide(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            rodString: String,
            rodUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            pressureUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && isValid(rodString) && (isValid(forceString) || (isValid(motorString) && (isValid(flowString)||isValid(speedString))))) {
                var pressure = 0.0
                var nCylinder = numCylinder.toInt()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var bR = rodString.toDouble()
                bR = ConversionUtil.convertUnit(bR, rodUnit, LENGTH.CM)
                var force = 0.0
                if (isValid(forceString)) {
                    force = forceString.toDouble()
                    force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                    pressure = (4.0 * force) / (10.0 * nCylinder * PI * (bD.pow(2) - bR.pow(2)))
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var flow = 0.0
                    if (isValid(flowString)) {
                        flow = flowString.toDouble()
                        flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                    } else if (isValid(speedString)) {
                        var speed = speedString.toDouble()
                        speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                        flow =
                            (speed * PI * (bD.pow(2) - bR.pow(2)) * nCylinder) / 40.0
                    }
                    pressure = (motor * 600) / flow

                }
                pressure = ConversionUtil.convertUnit(pressure, PRESSURE.BAR, pressureUnit)
                return decimalFormat.format(pressure).toString()
            }
            return ""
        }

        fun calculatePowerpackPressureSide(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            pressureUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && (isValid(forceString) || (isValid(motorString) && (isValid(flowString)||isValid(speedString))))) {
                var pressure = 0.0
                var nCylinder = numCylinder.toInt()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var force = 0.0
                if (isValid(forceString)) {
                    force = forceString.toDouble()
                    force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                    pressure = (4.0 * force) / (10.0 * nCylinder * PI * bD.pow(2))
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var flow = 0.0
                    if (isValid(flowString)) {
                        flow = flowString.toDouble()
                        flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                    } else if (isValid(speedString)) {
                        var speed = speedString.toDouble()
                        speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                        flow =
                            (speed * PI * bD.pow(2) * nCylinder) / 40.0
                    }
                    pressure = (motor * 600) / flow
                }
                pressure = ConversionUtil.convertUnit(pressure, PRESSURE.BAR, pressureUnit)
                return decimalFormat.format(pressure).toString()
            }
            return "";
        }

        fun calculatePowerpackFlow(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            rodString: String,
            rodUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            flowUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && isValid(rodString) && (isValid(speedString) || (isValid(motorString) && (isValid(forceString)||isValid(pressureString))))) {
                var flow = 0.0
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var rD = rodString.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.CM)

                if (isValid(speedString)) {
                    var speed = speedString.toDouble()
                    speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                    flow = (PI * (bD.pow(2) - rD.pow(2)) * speed * nCylinder) / 40.0
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var pressure = 0.0
                    if (isValid(forceString)) {
                        var force = forceString.toDouble()
                        force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                        pressure = (4.0 * force) / (10.0 * nCylinder * PI * (bD.pow(2) - rD.pow(2)))
                    } else if (isValid(pressureString)) {
                        pressure = pressureString.toDouble()
                        pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                    }
                    flow = (motor * 600) / pressure;
                }
                flow = ConversionUtil.convertUnit(flow, FLOW.L_MIN, flowUnit)
                return decimalFormat.format(flow).toString()
            }
            return ""
        }

        fun calculatePowerpackFlow(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            flowUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && (isValid(speedString) || (isValid(motorString) && (isValid(forceString)||isValid(pressureString))))) {
                var flow = 0.0
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)

                if (isValid(speedString)) {
                    var speed = speedString.toDouble()
                    speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                    flow = (PI * bD.pow(2) * speed * nCylinder) / 40.0
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var pressure = 0.0
                    if (isValid(forceString)) {
                        var force = forceString.toDouble()
                        force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                        pressure = (4.0 * force) / (10.0 * nCylinder * PI * bD.pow(2))
                    } else if (isValid(pressureString)) {
                        pressure = pressureString.toDouble()
                        pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                    }
                    flow = (motor * 600) / pressure;
                }
                flow = ConversionUtil.convertUnit(flow, FLOW.L_MIN, flowUnit)
                return decimalFormat.format(flow).toString()
            }
            return ""
        }

        fun calculatePowerpackSpeed(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            rodString: String,
            rodUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            speedUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && isValid(rodString) && (isValid(flowString) || (isValid(motorString) && (isValid(forceString)||isValid(pressureString))))) {
                var speed = 0.0
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var rD = rodString.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.CM)

                var flow = 0.0
                if (isValid(flowString)) {
                    flow = flowString.toDouble()
                    flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var pressure = 0.0
                    if (isValid(forceString)) {
                        var force = forceString.toDouble()
                        force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                        pressure = (4.0 * force) / (10.0 * nCylinder * PI * (bD.pow(2) - rD.pow(2)))
                    } else if (isValid(pressureString)) {
                        pressure = pressureString.toDouble()
                        pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                    }
                    flow = (motor * 600) / pressure;
                }
                speed = (40.0 * flow) / (PI * (bD.pow(2) - rD.pow(2)) * nCylinder)
                speed = ConversionUtil.convertUnit(speed, SPEED.M_MIN, speedUnit)
                return decimalFormat.format(speed).toString()
            }
            return ""
        }

        fun calculatePowerpackSpeed(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            motorString: String,
            motorUnit: UNIT,
            speedUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && (isValid(flowString) || (isValid(motorString) && (isValid(forceString)||isValid(pressureString))))) {
                var speed = 0.0
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)

                var flow = 0.0
                if (isValid(flowString)) {
                    flow = flowString.toDouble()
                    flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                } else if (isValid(motorString)) {
                    var motor = motorString.toDouble()
                    motor = ConversionUtil.convertUnit(motor, motorUnit, ENERGY.KW)
                    var pressure = 0.0
                    if (isValid(forceString)) {
                        var force = forceString.toDouble()
                        force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                        pressure = (4.0 * force) / (10.0 * nCylinder * PI * bD.pow(2))
                    } else if (isValid(pressureString)) {
                        pressure = pressureString.toDouble()
                        pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                    }
                    flow = (motor * 600) / pressure;
                }
                speed = (40.0 * flow) / (PI * bD.pow(2) * nCylinder)
                speed = ConversionUtil.convertUnit(speed, SPEED.M_MIN, speedUnit)
                return decimalFormat.format(speed).toString()
            }
            return ""
        }

        fun calculatePowerpackMotor(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            rodString: String,
            rodUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            motorUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && isValid(rodString) && ((isValid(flowString) && (isValid(pressureString) || isValid(forceString))) || (isValid(speedString) && (isValid(pressureString) || isValid(forceString))))) {
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var rD = rodString.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.CM)

                var pressure = 0.0
                var flow = 0.0
                var motor = 0.0

                if (isValid(pressureString)) {
                    pressure = pressureString.toDouble()
                    pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                }

                if (isValid(forceString)) {
                    var force = forceString.toDouble()
                    force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                    pressure = (4.0 * force) / (10.0 * nCylinder * PI * (bD.pow(2) - rD.pow(2)))
                }

                if (isValid(flowString)) {
                    flow = flowString.toDouble()
                    flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                }

                if (isValid(speedString)) {
                    var speed = speedString.toDouble()
                    speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                    flow = (PI * (bD.pow(2) - rD.pow(2)) * speed * nCylinder) / 40.0
                }
                motor = (pressure * flow) / 600
                Log.d(TAG, "Motor: $motor")
                motor = ConversionUtil.convertUnit(motor, ENERGY.KW, motorUnit)
                return decimalFormat.format(motor).toString()
            }
            return "";
        }

        fun calculatePowerpackMotor(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            pressureString: String,
            pressureUnit: UNIT,
            forceString: String,
            forceUnit: UNIT,
            flowString: String,
            flowUnit: UNIT,
            speedString: String,
            speedUnit: UNIT,
            motorUnit: UNIT
        ): String {
            if (isValid(numCylinder) && isValid(boreString) && ((isValid(flowString) && (isValid(pressureString) || isValid(forceString))) || (isValid(speedString) && (isValid(pressureString) || isValid(forceString))))) {
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.CM)
                var pressure = 0.0
                var flow = 0.0
                var motor = 0.0

                if (isValid(pressureString)) {
                    pressure = pressureString.toDouble()
                    pressure = ConversionUtil.convertUnit(pressure, pressureUnit, PRESSURE.BAR)
                    Log.d(TAG, "Pressure: $pressure")
                }

                if (isValid(forceString)) {
                    var force = forceString.toDouble()
                    force = ConversionUtil.convertUnit(force, forceUnit, FORCE.NEWTON)
                    pressure = (4.0 * force) / (10.0 * nCylinder * PI * bD.pow(2))
                    Log.d(TAG, "Pressure: $pressure")
                }

                if (isValid(flowString)) {
                    flow = flowString.toDouble()
                    flow = ConversionUtil.convertUnit(flow, flowUnit, FLOW.L_MIN)
                    Log.d(TAG, "Flow: $flow")
                }

                if (isValid(speedString)) {
                    var speed = speedString.toDouble()
                    speed = ConversionUtil.convertUnit(speed, speedUnit, SPEED.M_MIN)
                    flow = (PI * bD.pow(2) * speed * nCylinder) / 40.0
                    Log.d(TAG, "Flow: $flow")
                }
                motor = (pressure * flow) / 600
                Log.d(TAG, "Motor: $motor")
                motor = ConversionUtil.convertUnit(motor, ENERGY.KW, motorUnit)
                Log.d(TAG, "Motor: $motor")
                return decimalFormat.format(motor).toString()
            }
            return "";
        }

        fun calculateTankCapacity(flowUpString: String, flowDownString: String, flowPressingString: String, flowUnit: UNIT, tankUnit: UNIT): String {
            if (isValid(flowUpString) || isValid(flowDownString) || isValid(flowPressingString)) {
                var tankCapacity = 0.0
                if (isValid(flowUpString)) {
                    var flowUp = flowUpString.toDouble()
                    flowUp = ConversionUtil.convertUnit(flowUp, flowUnit, FLOW.L_MIN)
                    tankCapacity = max(tankCapacity, flowUp)
                }

                if (isValid(flowDownString)) {
                    var flowDown = flowDownString.toDouble()
                    flowDown = ConversionUtil.convertUnit(flowDown, flowUnit, FLOW.L_MIN)
                    tankCapacity = max(tankCapacity, flowDown)
                }

                if (isValid(flowPressingString)) {
                    var flowPressing = flowPressingString.toDouble()
                    flowPressing = ConversionUtil.convertUnit(flowPressing, flowUnit, FLOW.L_MIN)
                    tankCapacity = max(tankCapacity, flowPressing)
                }

                tankCapacity = tankCapacity * 4
                tankCapacity = ConversionUtil.convertUnit(tankCapacity, VOLUME.LITRE, tankUnit)
                return decimalFormat.format(tankCapacity).toString()
            }
            return ""
        }

        fun calculatePowerpackOilStroke(
            numCylinder: String,
            boreString: String,
            boreUnit: UNIT,
            strokeString: String,
            strokeUnit: UNIT,
            volOilStrokeUnit: UNIT
        ): String {

            if (isValid(numCylinder) && isValid(boreString) && isValid(strokeString)) {
                var nCylinder = numCylinder.toDouble()
                var bD = boreString.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.METER)
                var stroke = strokeString.toDouble()
                stroke = ConversionUtil.convertUnit(stroke, strokeUnit, LENGTH.METER)
                var volOilStroke = (PI * bD.pow(2) * stroke * nCylinder)/4.0
                volOilStroke = ConversionUtil.convertUnit(volOilStroke, VOLUME.M3, volOilStrokeUnit)
                return decimalFormat.format(volOilStroke).toString()
            }
            return ""
        }

    }
}