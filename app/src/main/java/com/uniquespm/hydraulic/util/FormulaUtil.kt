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
            rodDiameter: String,
            pressure: String,
            forceRodSide: String
        ): String {
            if (isValid(boreDiameter) && (isValid(pressure) || (isValid(rodDiameter) && isValid(
                    forceRodSide
                )))
            ) {
                return if (isValid(boreDiameter) && isValid(pressure)) {
                    val bD = boreDiameter.toDouble()
                    val pR = pressure.toDouble()
                    decimalFormat.format((PI * bD * pR) / 4.0).toString()
                } else {
                    val bD = boreDiameter.toDouble()
                    val rD = rodDiameter.toDouble()
                    val fR = forceRodSide.toDouble()
                    decimalFormat.format((bD.pow(2.0) * fR) / (bD.pow(2.0) - rD.pow(2.0))).toString()
                }
            }
            return ""
        }

        fun calculateForceRodSide(
            boreDiameter: String,
            rodDiameter: String,
            pressure: String,
            forceBoreSide: String
        ) : String {
            if (isValid(boreDiameter) && isValid(rodDiameter) && ((isValid(pressure)) || isValid(forceBoreSide))) {
                val bD = boreDiameter.toDouble()
                val rD = rodDiameter.toDouble()
                return if (isValid(boreDiameter) && isValid(rodDiameter) && isValid(pressure)) {
                    val pR = pressure.toDouble()
                    decimalFormat.format((PI * (bD.pow(2.0) - (rD.pow(2.0))) * pR * 10)/4.0).toString()
                } else {
                    val fB = forceBoreSide.toDouble()
                    decimalFormat.format(((bD.pow(2) - rD.pow(2)) * fB) / bD.pow(2.0)).toString()
                }
            }
            return ""
        }

        fun calculatePressure(
            boreDiameter: String,
            rodDiameter: String,
            forceRodSide: String,
            forceBoreSide: String
        ) : String {
            if(isValid(boreDiameter) && (isValid(forceBoreSide) || (isValid(rodDiameter) && isValid(forceRodSide)))) {
                val bD = boreDiameter.toDouble()
                return if (isValid(boreDiameter) && isValid(forceBoreSide)) {
                    val fB = forceBoreSide.toDouble()
                    decimalFormat.format((4.0 * fB) / (PI * bD.pow(2.0) * 10.0)).toString()
                } else {
                    val rD = rodDiameter.toDouble()
                    val fR = forceRodSide.toDouble()
                    decimalFormat.format((4.0 * fR)/(PI * (bD.pow(2.0) - rD.pow(2.0)) * 10)).toString()
                }
            }
            return ""
        }

        fun calculateAreaBoreSide(
            boreDiameter: String
        ) : String {
            if (isValid(boreDiameter)) {
                val bD = boreDiameter.toDouble()
                return decimalFormat.format((PI * bD.pow(2.0)) / 4.0).toString()
            }
            return ""
        }

        fun calculateAreaRodSide(
            boreDiameter: String,
            rodDiameter: String
        ) : String {
            if (isValid(boreDiameter) && isValid(rodDiameter)) {
                val bD = boreDiameter.toDouble()
                val rD = rodDiameter.toDouble()
                return decimalFormat.format((PI * (bD.pow(2.0) - rD.pow(2.0))) / 4.0).toString()
            }
            return ""
        }

        fun calculateVolumeBoreSide(
            boreDiameter: String,
            stroke: String
        ) : String {
            if (isValid(boreDiameter) && isValid(stroke)) {
                val bD = boreDiameter.toDouble()
                val str = stroke.toDouble()
                return decimalFormat.format((PI * bD.pow(2.0) * str) / 4.0).toString()
            }
            return ""
        }

        fun calculateVolumeRodSide(
            boreDiameter: String,
            rodDiameter: String,
            stroke: String
        ) : String {
            if (isValid(boreDiameter) && isValid(stroke) && isValid(rodDiameter)) {
                val bD = boreDiameter.toDouble()
                val rD = rodDiameter.toDouble()
                val str = stroke.toDouble()
                return decimalFormat.format((PI * (bD.pow(2.0) - rD.pow(2.0)) * str) / 4.0).toString()
            }
            return ""
        }
    }
}