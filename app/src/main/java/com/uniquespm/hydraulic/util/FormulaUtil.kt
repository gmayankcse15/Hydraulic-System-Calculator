package com.uniquespm.hydraulic.util

import android.widget.EditText
import java.text.DecimalFormat
import kotlin.math.PI
import kotlin.math.pow

class FormulaUtil {

    companion object {

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
        private val decimalFormat: DecimalFormat = DecimalFormat("#.#####")

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
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.METER)
                return if (isValid(boreDiameter) && isValid(pressure)) {
                    var pR = pressure.toDouble()
                    pR = ConversionUtil.convertUnit(pR, pressureUnit, PRESSURE.BAR)
                    var res: Double = (PI * bD * pR) / 4.0
                    res = ConversionUtil.convertUnit(res, FORCE.TON, toForceUnit)
                    decimalFormat.format(res).toString()
                } else {
                    var rD = rodDiameter.toDouble()
                    rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.METER)
                    var fR = forceRodSide.toDouble()
                    fR = ConversionUtil.convertUnit(fR, forceUnit, FORCE.TON)
                    var res = (bD.pow(2.0) * fR) / (bD.pow(2.0) - rD.pow(2.0))
                    res = ConversionUtil.convertUnit(res, FORCE.TON, toForceUnit)
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
        ) : String {
            if (isValid(boreDiameter) && isValid(rodDiameter) && ((isValid(pressure)) || isValid(forceBoreSide))) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.METER)
                var rD = rodDiameter.toDouble()
                rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.METER)
                return if (isValid(boreDiameter) && isValid(rodDiameter) && isValid(pressure)) {
                    var pR = pressure.toDouble()
                    pR = ConversionUtil.convertUnit(pR, pressureUnit, PRESSURE.BAR)
                    var res = (PI * (bD.pow(2.0) - (rD.pow(2.0))) * pR * 10)/4.0
                    res = ConversionUtil.convertUnit(res, FORCE.TON, toForceUnit)
                    decimalFormat.format(res).toString()
                } else {
                    var fB = forceBoreSide.toDouble()
                    fB = ConversionUtil.convertUnit(fB, forceUnit, FORCE.TON)
                    var res = ((bD.pow(2) - rD.pow(2)) * fB) / bD.pow(2.0)
                    res = ConversionUtil.convertUnit(res, FORCE.TON, toForceUnit)
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
            toPressureUnit : UNIT
        ) : String {
            if(isValid(boreDiameter) && (isValid(forceBoreSide) || (isValid(rodDiameter) && isValid(forceRodSide)))) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.METER)
                return if (isValid(boreDiameter) && isValid(forceBoreSide)) {
                    var fB = forceBoreSide.toDouble()
                    fB = ConversionUtil.convertUnit(fB, forceUnit, FORCE.TON)
                    var res = (4.0 * fB) / (PI * bD.pow(2.0) * 10.0)
                    res = ConversionUtil.convertUnit(res, PRESSURE.BAR, toPressureUnit)
                    decimalFormat.format(res).toString()
                } else {
                    var rD = rodDiameter.toDouble()
                    rD = ConversionUtil.convertUnit(rD, rodUnit, LENGTH.METER)
                    var fR = forceRodSide.toDouble()
                    fR = ConversionUtil.convertUnit(fR, forceUnit, FORCE.TON)
                    var res = (4.0 * fR)/(PI * (bD.pow(2.0) - rD.pow(2.0)) * 10)
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
        ) : String {
            if (isValid(boreDiameter)) {
                var bD = boreDiameter.toDouble()
                bD = ConversionUtil.convertUnit(bD, boreUnit, LENGTH.MM)
                var res = (PI * bD.pow(2.0)) / 4.0
                res = ConversionUtil.convertUnit(res, AREA.MM2, toAreaUnit)
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
        ) : String {
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
        ) : String {
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
        ) : String {
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
    }
}