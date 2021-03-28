package com.uniquespm.hydraulic.ui.powerpack

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.common.CustomSpinnerAdapter
import com.uniquespm.hydraulic.common.DecimalDigitInputFilter
import com.uniquespm.hydraulic.common.SaveProjectCallback
import com.uniquespm.hydraulic.model.DataRepository
import com.uniquespm.hydraulic.model.Powerpack
import com.uniquespm.hydraulic.ui.cylinder.EditTextChangeListener
import com.uniquespm.hydraulic.util.*
import kotlinx.android.synthetic.main.fragment_cylinder.*
import kotlinx.android.synthetic.main.fragment_powerpack.*
import kotlinx.android.synthetic.main.fragment_powerpack.bore_edit_text
import kotlinx.android.synthetic.main.fragment_powerpack.bore_spinner
import kotlinx.android.synthetic.main.fragment_powerpack.rod_edit_text
import kotlinx.android.synthetic.main.fragment_powerpack.rod_spinner
import kotlinx.android.synthetic.main.fragment_powerpack.save_button
import kotlinx.android.synthetic.main.fragment_powerpack.share_button
import kotlinx.android.synthetic.main.fragment_powerpack.stroke_edit_text
import kotlinx.android.synthetic.main.fragment_powerpack.stroke_spinner
import kotlinx.android.synthetic.main.powerpack_table_layout.*
import kotlinx.android.synthetic.main.powerpack_table_layout.force_spinner
import kotlinx.android.synthetic.main.powerpack_table_layout.pressure_spinner
import kotlinx.android.synthetic.main.powerpack_table_layout.view.*
import java.text.Normalizer

class PowerpackFragment : Fragment() {
    
    companion object {
        const val TAG = "PowerpackFragment"
    }
    private lateinit var powerpackViewModel: PowerpackViewModel
    private lateinit var mContext: Context
    private val unitLength : Array<UNIT> = arrayOf(LENGTH.MM, LENGTH.CM, LENGTH.INCH)
    private val unitPressure : Array<UNIT> = arrayOf(PRESSURE.BAR, PRESSURE.PSI, PRESSURE.KGCM2)
    private val unitVolume : Array<UNIT> = arrayOf(VOLUME.LITRE)
    private val unitForce : Array<UNIT> = arrayOf(FORCE.TON, FORCE.NEWTON)
    private val unitSpeed : Array<UNIT> = arrayOf(SPEED.MM_SEC, SPEED.M_MIN)
    private val unitFlow : Array<UNIT> = arrayOf(FLOW.L_MIN, FLOW.M3_MIN, FLOW.FPM)
    private val unitEnergy: Array<UNIT> = arrayOf(ENERGY.HP, ENERGY.KW)
    private var mPowerpackData: Powerpack? = null

    private val spinnerDataArray = arrayOf(
        unitLength,
        unitLength,
        unitLength,
        unitForce,
        unitSpeed,
        unitPressure,
        unitFlow,
        unitEnergy,
        unitVolume,
        unitVolume
    )
    private var mSpinnerEditTextMap: MutableMap<Int, Array<EditText>>? = null
    private var mSpinnerIdCurrentUnitMap: MutableMap<Int, UNIT>? = null
    private var mEditTextSpinnerMap: MutableMap<Int, AppCompatSpinner>? = null
    private var mEditTextTextWatcherMap: MutableMap<EditText, EditTextInputWatcher>? = null
    private var inputSet: MutableSet<EditText>? = null
    private var mDataRepository: DataRepository? = null

    private val mSaveProjectListener = object: SaveProjectCallback {
        override fun onSave(projectName: String) {
            mDataRepository = DataRepository(mContext.applicationContext)
            bore_spinner.selectedItemPosition
            val powerpack = Powerpack(
                projectName,
                Constants.POWERPACK,
                cyclinder_edit_text.text.toString().toInt(),
                FormulaUtil.getValidData(bore_edit_text, inputSet),
                bore_spinner.selectedItemPosition,
                FormulaUtil.getValidData(rod_edit_text, inputSet),
                rod_spinner.selectedItemPosition,
                FormulaUtil.getValidData(stroke_edit_text, inputSet),
                stroke_spinner.selectedItemPosition,
                FormulaUtil.getValidData(up_force_edit_text, inputSet),
                FormulaUtil.getValidData(down_force_edit_text, inputSet),
                FormulaUtil.getValidData(pressing_force_edit_text, inputSet),
                force_spinner.selectedItemPosition,
                FormulaUtil.getValidData(up_speed_edit_text, inputSet),
                FormulaUtil.getValidData(speed_down_edit_text, inputSet),
                FormulaUtil.getValidData(speed_down_edit_text, inputSet),
                speed_spinner.selectedItemPosition,
                FormulaUtil.getValidData(up_flow_edit_text, inputSet),
                FormulaUtil.getValidData(down_flow_edit_text, inputSet),
                FormulaUtil.getValidData(pressing_flow_edit_text, inputSet),
                flow_spinner.selectedItemPosition,
                FormulaUtil.getValidData(up_motor_edit_text, inputSet),
                FormulaUtil.getValidData(down_motor_edit_text, inputSet),
                FormulaUtil.getValidData(pressing_motor_edit_text, inputSet),
                motor_spinner.selectedItemPosition
            )
            Log.d(TAG, "powerpack $powerpack")
            mDataRepository?.insertPowerpack(powerpack)
        }

    }

    private val mEditTextChangeListener = object : EditTextChangeListener {
        override fun onTextChangeComplete(editText: EditText) {
            if (inputSet?.contains(editText) == false) {
                return
            }
            when(editText) {
                up_pressure_edit_text -> {
                    if (up_pressure_edit_text.text.isNotEmpty()) {
                        disableEditText(up_force_edit_text)
                        inputSet?.remove(up_force_edit_text)
                        if ((up_speed_edit_text.text.isNotEmpty() && inputSet?.contains(up_speed_edit_text) == true) || (up_flow_edit_text.text.isNotEmpty() && inputSet?.contains(up_flow_edit_text) == true)) {
                            inputSet?.remove(up_motor_edit_text)
                            disableEditText(up_motor_edit_text)
                        }
                        if (up_motor_edit_text.text.isNotEmpty() && inputSet?.contains(up_motor_edit_text) == true) {
                            inputSet?.remove(up_speed_edit_text)
                            inputSet?.remove(up_flow_edit_text)
                            disableEditText(up_speed_edit_text)
                            disableEditText(up_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(up_force_edit_text)
                        enableEditText(up_motor_edit_text)

                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            enableEditText(up_speed_edit_text)
                            enableEditText(up_flow_edit_text)
                        }

                        calculateResult()

                        inputSet?.add(up_force_edit_text)
                        inputSet?.add(up_motor_edit_text)
                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            inputSet?.add(up_speed_edit_text)
                            inputSet?.add(up_flow_edit_text)
                        }
                    }
                }
                down_pressure_edit_text -> {
                    if (down_pressure_edit_text.text.isNotEmpty()) {
                        disableEditText(down_force_edit_text)
                        inputSet?.remove(down_force_edit_text)
                        if ((speed_down_edit_text.text.isNotEmpty() && inputSet?.contains(speed_down_edit_text) == true) || (down_flow_edit_text.text.isNotEmpty() && inputSet?.contains(down_flow_edit_text) == true)) {
                            inputSet?.remove(down_motor_edit_text)
                            disableEditText(down_motor_edit_text)
                        }
                        if (down_motor_edit_text.text.isNotEmpty() && inputSet?.contains(down_motor_edit_text) == true) {
                            inputSet?.remove(speed_down_edit_text)
                            inputSet?.remove(down_flow_edit_text)
                            disableEditText(speed_down_edit_text)
                            disableEditText(down_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(down_force_edit_text)
                        enableEditText(down_motor_edit_text)
                        if (inputSet?.contains(speed_down_edit_text) == false && inputSet?.contains(down_flow_edit_text) == false) {
                            enableEditText(speed_down_edit_text)
                            enableEditText(down_flow_edit_text)
                        }

                        calculateResult()

                        inputSet?.add(down_force_edit_text)
                        inputSet?.add(down_motor_edit_text)
                        if (inputSet?.contains(speed_down_edit_text) == false && inputSet?.contains(down_flow_edit_text) == false) {
                            inputSet?.add(speed_down_edit_text)
                            inputSet?.add(down_flow_edit_text)
                        }
                    }
                }
                pressure_pressing_edit_text -> {
                    if (pressure_pressing_edit_text.text.isNotEmpty()) {
                        disableEditText(pressing_force_edit_text)
                        inputSet?.remove(pressing_force_edit_text)
                        if ((speed_pressing_edit_text.text.isNotEmpty() && inputSet?.contains(speed_pressing_edit_text) == true) || (pressing_flow_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_flow_edit_text) == true)) {
                            inputSet?.remove(pressing_motor_edit_text)
                            disableEditText(pressing_motor_edit_text)
                        }
                        if (pressing_motor_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_motor_edit_text) == true) {
                            inputSet?.remove(speed_pressing_edit_text)
                            inputSet?.remove(pressing_flow_edit_text)
                            disableEditText(speed_pressing_edit_text)
                            disableEditText(pressing_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(pressing_force_edit_text)
                        enableEditText(pressing_motor_edit_text)
                        if (inputSet?.contains(speed_pressing_edit_text) == false && inputSet?.contains(pressing_flow_edit_text) == false) {
                            enableEditText(speed_pressing_edit_text)
                            enableEditText(pressing_flow_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(pressing_force_edit_text)
                        inputSet?.add(pressing_motor_edit_text)
                        if (inputSet?.contains(speed_pressing_edit_text) == false && inputSet?.contains(pressing_flow_edit_text) == false) {
                            inputSet?.add(speed_pressing_edit_text)
                            inputSet?.add(pressing_flow_edit_text)
                        }
                    }
                }
                up_force_edit_text -> {
                    if (up_force_edit_text.text.isNotEmpty()) {
                        disableEditText(up_pressure_edit_text)
                        inputSet?.remove(up_pressure_edit_text)
                        if ((up_speed_edit_text.text.isNotEmpty() && inputSet?.contains(up_speed_edit_text) == true) || (up_flow_edit_text.text.isNotEmpty() && inputSet?.contains(up_flow_edit_text) == true)) {
                            inputSet?.remove(up_motor_edit_text)
                            disableEditText(up_motor_edit_text)
                        }
                        if (up_motor_edit_text.text.isNotEmpty() && inputSet?.contains(up_motor_edit_text) == true) {
                            inputSet?.remove(up_speed_edit_text)
                            inputSet?.remove(up_flow_edit_text)
                            disableEditText(up_speed_edit_text)
                            disableEditText(up_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(up_pressure_edit_text)
                        enableEditText(up_motor_edit_text)
                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            enableEditText(up_speed_edit_text)
                            enableEditText(up_flow_edit_text)
                        }

                        calculateResult()

                        inputSet?.add(up_pressure_edit_text)
                        inputSet?.add(up_motor_edit_text)
                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            inputSet?.add(up_speed_edit_text)
                            inputSet?.add(up_flow_edit_text)
                        }
                    }
                }
                down_force_edit_text -> {
                    if (down_force_edit_text.text.isNotEmpty()) {
                        disableEditText(down_pressure_edit_text)
                        inputSet?.remove(down_pressure_edit_text)
                        if ((speed_down_edit_text.text.isNotEmpty() && inputSet?.contains(speed_down_edit_text) == true) || (down_flow_edit_text.text.isNotEmpty() && inputSet?.contains(down_flow_edit_text) == true)) {
                            inputSet?.remove(down_motor_edit_text)
                            disableEditText(down_motor_edit_text)
                        }
                        if (down_motor_edit_text.text.isNotEmpty() && inputSet?.contains(down_motor_edit_text) == true) {
                            inputSet?.remove(speed_down_edit_text)
                            inputSet?.remove(down_flow_edit_text)
                            disableEditText(speed_down_edit_text)
                            disableEditText(down_flow_edit_text)
                        }

                        calculateResult()
                    } else {
                        enableEditText(down_pressure_edit_text)
                        enableEditText(down_motor_edit_text)
                        if (inputSet?.contains(speed_down_edit_text) == false && inputSet?.contains(down_flow_edit_text) == false) {
                            enableEditText(speed_down_edit_text)
                            enableEditText(down_flow_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(down_pressure_edit_text)
                        inputSet?.add(down_motor_edit_text)
                        if (inputSet?.contains(speed_down_edit_text) == false && inputSet?.contains(down_flow_edit_text) == false) {
                            inputSet?.add(speed_down_edit_text)
                            inputSet?.add(down_flow_edit_text)
                        }
                    }
                }
                pressing_force_edit_text -> {
                    if (pressing_force_edit_text.text.isNotEmpty()) {
                        disableEditText(pressure_pressing_edit_text)
                        inputSet?.remove(pressure_pressing_edit_text)
                        if ((speed_pressing_edit_text.text.isNotEmpty() && inputSet?.contains(speed_pressing_edit_text) == true) || (pressing_flow_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_flow_edit_text) == true)) {
                            inputSet?.remove(pressing_motor_edit_text)
                            disableEditText(pressing_motor_edit_text)
                        }
                        if (pressing_motor_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_motor_edit_text) == true) {
                            inputSet?.remove(speed_pressing_edit_text)
                            inputSet?.remove(pressing_flow_edit_text)
                            disableEditText(speed_pressing_edit_text)
                            disableEditText(pressing_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(pressure_pressing_edit_text)
                        enableEditText(pressing_motor_edit_text)
                        if (inputSet?.contains(speed_pressing_edit_text) == false && inputSet?.contains(pressing_flow_edit_text) == false) {
                            enableEditText(speed_pressing_edit_text)
                            enableEditText(pressing_flow_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(pressure_pressing_edit_text)
                        inputSet?.add(pressing_motor_edit_text)
                        if (inputSet?.contains(speed_pressing_edit_text) == false && inputSet?.contains(pressing_flow_edit_text) == false) {
                            inputSet?.add(speed_pressing_edit_text)
                            inputSet?.add(pressing_flow_edit_text)
                        }
                    }
                }
                up_speed_edit_text -> {
                    if (up_speed_edit_text.text.isNotEmpty()) {
                        disableEditText(up_flow_edit_text)
                        inputSet?.remove(up_flow_edit_text)
                        if ((up_pressure_edit_text.text.isNotEmpty() && inputSet?.contains(up_pressure_edit_text) == true) || (up_force_edit_text.text.isNotEmpty() && inputSet?.contains(up_force_edit_text) == true)) {
                            inputSet?.remove(up_motor_edit_text)
                            disableEditText(up_motor_edit_text)
                        }
                        if (up_motor_edit_text.text.isNotEmpty() && inputSet?.contains(up_motor_edit_text) == true) {
                            inputSet?.remove(up_pressure_edit_text)
                            inputSet?.remove(up_force_edit_text)
                            disableEditText(up_pressure_edit_text)
                            disableEditText(up_force_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(up_flow_edit_text)
                        enableEditText(up_motor_edit_text)
                        if (inputSet?.contains(up_pressure_edit_text) == false && inputSet?.contains(up_force_edit_text) == false) {
                            enableEditText(up_pressure_edit_text)
                            enableEditText(up_force_edit_text)
                        }

                        calculateResult()

                        inputSet?.add(up_flow_edit_text)
                        inputSet?.add(up_motor_edit_text)
                        if (inputSet?.contains(up_pressure_edit_text) == false && inputSet?.contains(up_force_edit_text) == false) {
                            inputSet?.add(up_pressure_edit_text)
                            inputSet?.add(up_force_edit_text)
                        }
                    }
                }
                speed_down_edit_text -> {
                    if (speed_down_edit_text.text.isNotEmpty()) {
                        disableEditText(down_flow_edit_text)
                        inputSet?.remove(down_flow_edit_text)
                        if ((down_pressure_edit_text.text.isNotEmpty() && inputSet?.contains(down_pressure_edit_text) == true) || (down_force_edit_text.text.isNotEmpty() && inputSet?.contains(down_force_edit_text) == true)) {
                            inputSet?.remove(down_motor_edit_text)
                            disableEditText(down_motor_edit_text)
                        }
                        if (down_motor_edit_text.text.isNotEmpty() && inputSet?.contains(down_motor_edit_text) == true) {
                            inputSet?.remove(down_pressure_edit_text)
                            inputSet?.remove(down_force_edit_text)
                            disableEditText(down_pressure_edit_text)
                            disableEditText(down_force_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(down_flow_edit_text)
                        enableEditText(down_motor_edit_text)
                        if (inputSet?.contains(down_pressure_edit_text) == false && inputSet?.contains(down_force_edit_text) == false) {
                            enableEditText(down_pressure_edit_text)
                            enableEditText(down_force_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(down_flow_edit_text)
                        inputSet?.add(down_motor_edit_text)
                        if (inputSet?.contains(down_pressure_edit_text) == false && inputSet?.contains(down_force_edit_text) == false) {
                            inputSet?.add(down_pressure_edit_text)
                            inputSet?.add(down_force_edit_text)
                        }
                    }
                }
                speed_pressing_edit_text -> {
                    if (speed_pressing_edit_text.text.isNotEmpty()) {
                        disableEditText(pressing_flow_edit_text)
                        inputSet?.remove(pressing_flow_edit_text)
                        if ((pressure_pressing_edit_text.text.isNotEmpty() && inputSet?.contains(pressure_pressing_edit_text) == true) || (pressing_force_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_force_edit_text) == true)) {
                            inputSet?.remove(pressing_motor_edit_text)
                            disableEditText(pressing_motor_edit_text)
                        }
                        if (pressing_motor_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_motor_edit_text) == true) {
                            inputSet?.remove(pressure_pressing_edit_text)
                            inputSet?.remove(pressing_force_edit_text)
                            disableEditText(pressure_pressing_edit_text)
                            disableEditText(pressing_force_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(pressing_flow_edit_text)
                        enableEditText(pressing_motor_edit_text)
                        if (inputSet?.contains(pressure_pressing_edit_text) == false && inputSet?.contains(pressing_force_edit_text) == false) {
                            enableEditText(pressure_pressing_edit_text)
                            enableEditText(pressing_force_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(pressing_flow_edit_text)
                        inputSet?.add(pressing_motor_edit_text)
                        if (inputSet?.contains(pressure_pressing_edit_text) == false && inputSet?.contains(pressing_force_edit_text) == false) {
                            inputSet?.add(pressure_pressing_edit_text)
                            inputSet?.add(pressing_force_edit_text)
                        }
                    }
                }
                up_flow_edit_text -> {
                    if (up_flow_edit_text.text.isNotEmpty()) {
                        disableEditText(up_speed_edit_text)
                        inputSet?.remove(up_speed_edit_text)
                        if ((up_pressure_edit_text.text.isNotEmpty() && inputSet?.contains(up_pressure_edit_text) == true) || (up_force_edit_text.text.isNotEmpty()) && inputSet?.contains(up_force_edit_text) == true) {
                            inputSet?.remove(up_motor_edit_text)
                            disableEditText(up_motor_edit_text)
                        }
                        if (up_motor_edit_text.text.isNotEmpty() && inputSet?.contains(up_motor_edit_text) == true) {
                            inputSet?.remove(up_pressure_edit_text)
                            inputSet?.remove(up_force_edit_text)
                            disableEditText(up_pressure_edit_text)
                            disableEditText(up_force_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(up_speed_edit_text)
                        enableEditText(up_motor_edit_text)

                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            enableEditText(up_pressure_edit_text)
                            enableEditText(up_force_edit_text)
                        }
                        calculateResult()

                        inputSet?.add(up_speed_edit_text)
                        inputSet?.add(up_motor_edit_text)
                        if (inputSet?.contains(up_pressure_edit_text) == false && inputSet?.contains(up_force_edit_text) == false) {
                            inputSet?.add(up_pressure_edit_text)
                            inputSet?.add(up_force_edit_text)
                        }
                    }
                }
                down_flow_edit_text -> {
                    if (down_flow_edit_text.text.isNotEmpty()) {
                        disableEditText(speed_down_edit_text)
                        inputSet?.remove(speed_down_edit_text)
                        if ((down_pressure_edit_text.text.isNotEmpty() && inputSet?.contains(down_pressure_edit_text) == true) || (down_force_edit_text.text.isNotEmpty() && inputSet?.contains(down_force_edit_text) == true)) {
                            inputSet?.remove(down_motor_edit_text)
                            disableEditText(down_motor_edit_text)
                        }
                        if (down_motor_edit_text.text.isNotEmpty() && inputSet?.contains(down_motor_edit_text) == true) {
                            inputSet?.remove(down_pressure_edit_text)
                            inputSet?.remove(down_force_edit_text)
                            disableEditText(down_pressure_edit_text)
                            disableEditText(down_force_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(speed_down_edit_text)
                        enableEditText(down_motor_edit_text)
                        if (inputSet?.contains(down_pressure_edit_text) == false && inputSet?.contains(down_force_edit_text) == false) {
                            enableEditText(down_pressure_edit_text)
                            enableEditText(down_force_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(speed_down_edit_text)
                        inputSet?.add(down_motor_edit_text)
                        if (inputSet?.contains(down_pressure_edit_text) == false && inputSet?.contains(down_force_edit_text) == false) {
                            inputSet?.add(down_pressure_edit_text)
                            inputSet?.add(down_force_edit_text)
                        }
                    }
                }
                pressing_flow_edit_text -> {
                    if (pressing_flow_edit_text.text.isNotEmpty()) {
                        disableEditText(speed_pressing_edit_text)
                        inputSet?.remove(speed_pressing_edit_text)
                        if ((pressure_pressing_edit_text.text.isNotEmpty() && inputSet?.contains(pressure_pressing_edit_text) == true) || (pressing_force_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_force_edit_text) == true)) {
                            inputSet?.remove(pressing_motor_edit_text)
                            disableEditText(pressing_motor_edit_text)
                        }
                        if (pressing_motor_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_motor_edit_text) == true) {
                            inputSet?.remove(pressure_pressing_edit_text)
                            inputSet?.remove(pressing_force_edit_text)
                            disableEditText(pressure_pressing_edit_text)
                            disableEditText(pressing_force_edit_text)
                        }
                        calculateResult()
                    } else {
                        enableEditText(speed_pressing_edit_text)
                        enableEditText(pressing_motor_edit_text)
                        if(inputSet?.contains(pressure_pressing_edit_text) == false && inputSet?.contains(pressing_force_edit_text) == false) {
                            enableEditText(pressure_pressing_edit_text)
                            enableEditText(pressing_force_edit_text)
                        }
                        calculateResult()
                        inputSet?.add(speed_pressing_edit_text)
                        inputSet?.add(pressing_motor_edit_text)
                        if(inputSet?.contains(pressure_pressing_edit_text) == false && inputSet?.contains(pressing_force_edit_text) == false) {
                            inputSet?.add(pressure_pressing_edit_text)
                            inputSet?.add(pressing_force_edit_text)
                        }
                    }
                }
                up_motor_edit_text -> {
                    if (up_motor_edit_text.text.isNotEmpty()) {
                        if (up_force_edit_text.text.isNotEmpty() && inputSet?.contains(up_force_edit_text) == true) {
                            disableEditText(up_pressure_edit_text)
                            disableEditText(up_flow_edit_text)
                            disableEditText(up_speed_edit_text)
                            inputSet?.remove(up_pressure_edit_text)
                            inputSet?.remove(up_flow_edit_text)
                            inputSet?.remove(up_speed_edit_text)
                        }
                        if (up_pressure_edit_text.text.isNotEmpty() && inputSet?.contains(up_pressure_edit_text) == true) {
                            disableEditText(up_force_edit_text)
                            disableEditText(up_flow_edit_text)
                            disableEditText(up_speed_edit_text)
                            inputSet?.remove(up_force_edit_text)
                            inputSet?.remove(up_flow_edit_text)
                            inputSet?.remove(up_speed_edit_text)

                        }
                        if (up_flow_edit_text.text.isNotEmpty() && inputSet?.contains(up_flow_edit_text) == true) {
                            disableEditText(up_force_edit_text)
                            disableEditText(up_pressure_edit_text)
                            disableEditText(up_speed_edit_text)
                            inputSet?.remove(up_force_edit_text)
                            inputSet?.remove(up_pressure_edit_text)
                            inputSet?.remove(up_speed_edit_text)
                        }
                        if (up_speed_edit_text.text.isNotEmpty() && inputSet?.contains(up_speed_edit_text) == true) {
                            disableEditText(up_force_edit_text)
                            disableEditText(up_pressure_edit_text)
                            disableEditText(up_flow_edit_text)
                            inputSet?.remove(up_force_edit_text)
                            inputSet?.remove(up_pressure_edit_text)
                            inputSet?.remove(up_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        calculateResult()
                        if (inputSet?.contains(up_force_edit_text) == false && inputSet?.contains(up_pressure_edit_text) == false) {
                            enableEditText(up_force_edit_text)
                            enableEditText(up_pressure_edit_text)
                        }

                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            enableEditText(up_speed_edit_text)
                            enableEditText(up_flow_edit_text)
                        }
                        if (inputSet?.contains(up_force_edit_text) == false && inputSet?.contains(up_pressure_edit_text) == false) {
                            inputSet?.add(up_force_edit_text)
                            inputSet?.add(up_pressure_edit_text)
                        }
                        if (inputSet?.contains(up_speed_edit_text) == false && inputSet?.contains(up_flow_edit_text) == false) {
                            inputSet?.add(up_speed_edit_text)
                            inputSet?.add(up_flow_edit_text)
                        }
                    }
                }
                down_motor_edit_text -> {
                    if (down_motor_edit_text.text.isNotEmpty() == true) {
                        if (down_force_edit_text.text.isNotEmpty() && inputSet?.contains(down_force_edit_text) == true) {
                            disableEditText(down_pressure_edit_text)
                            disableEditText(down_flow_edit_text)
                            disableEditText(speed_down_edit_text)
                            inputSet?.remove(down_pressure_edit_text)
                            inputSet?.remove(down_flow_edit_text)
                            inputSet?.remove(speed_down_edit_text)
                        }
                        if (down_pressure_edit_text.text.isNotEmpty() && inputSet?.contains(down_pressure_edit_text) == true) {
                            disableEditText(down_force_edit_text)
                            disableEditText(down_flow_edit_text)
                            disableEditText(speed_down_edit_text)
                            inputSet?.remove(down_force_edit_text)
                            inputSet?.remove(down_flow_edit_text)
                            inputSet?.remove(speed_down_edit_text)
                        }
                        if (down_flow_edit_text.text.isNotEmpty() && inputSet?.contains(down_flow_edit_text) == true) {
                            disableEditText(down_force_edit_text)
                            disableEditText(down_pressure_edit_text)
                            disableEditText(speed_down_edit_text)
                            inputSet?.remove(down_force_edit_text)
                            inputSet?.remove(down_pressure_edit_text)
                            inputSet?.remove(speed_down_edit_text)
                        }
                        if (speed_down_edit_text.text.isNotEmpty() && inputSet?.contains(speed_down_edit_text) == true) {
                            disableEditText(down_force_edit_text)
                            disableEditText(down_pressure_edit_text)
                            disableEditText(down_flow_edit_text)
                            inputSet?.remove(down_force_edit_text)
                            inputSet?.remove(down_pressure_edit_text)
                            inputSet?.remove(down_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        calculateResult()
                        if (inputSet?.contains(down_force_edit_text) == false && inputSet?.contains(down_pressure_edit_text) == false) {
                            enableEditText(down_force_edit_text)
                            enableEditText(down_pressure_edit_text)
                        }
                        if (inputSet?.contains(speed_down_edit_text) == false && inputSet?.contains(down_flow_edit_text) == false) {
                            enableEditText(speed_down_edit_text)
                            enableEditText(down_flow_edit_text)
                        }
                        if (inputSet?.contains(down_force_edit_text) == false && inputSet?.contains(down_pressure_edit_text) == false) {
                            inputSet?.add(down_force_edit_text)
                            inputSet?.add(down_pressure_edit_text)
                        }
                        if (inputSet?.contains(speed_down_edit_text) == false && inputSet?.contains(down_flow_edit_text) == false) {
                            inputSet?.add(speed_down_edit_text)
                            inputSet?.add(down_flow_edit_text)
                        }
                    }
                }
                pressing_motor_edit_text -> {
                    if (pressing_motor_edit_text.text.isNotEmpty() == true) {
                        if (pressing_force_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_force_edit_text) == true) {
                            disableEditText(pressure_pressing_edit_text)
                            disableEditText(pressing_flow_edit_text)
                            disableEditText(speed_pressing_edit_text)
                            inputSet?.remove(pressure_pressing_edit_text)
                            inputSet?.remove(pressing_flow_edit_text)
                            inputSet?.remove(speed_pressing_edit_text)
                        }
                        if (pressure_pressing_edit_text.text.isNotEmpty() && inputSet?.contains(pressure_pressing_edit_text) == true) {
                            disableEditText(pressing_force_edit_text)
                            disableEditText(pressing_flow_edit_text)
                            disableEditText(speed_pressing_edit_text)
                            inputSet?.remove(pressing_force_edit_text)
                            inputSet?.remove(pressing_flow_edit_text)
                            inputSet?.remove(speed_pressing_edit_text)
                        }
                        if (pressing_flow_edit_text.text.isNotEmpty() && inputSet?.contains(pressing_flow_edit_text) == true) {
                            disableEditText(pressing_force_edit_text)
                            disableEditText(pressure_pressing_edit_text)
                            disableEditText(speed_pressing_edit_text)
                            inputSet?.remove(pressing_force_edit_text)
                            inputSet?.remove(pressure_pressing_edit_text)
                            inputSet?.remove(speed_pressing_edit_text)
                        }
                        if (speed_pressing_edit_text.text.isNotEmpty() && inputSet?.contains(speed_pressing_edit_text) == true) {
                            disableEditText(pressing_force_edit_text)
                            disableEditText(pressure_pressing_edit_text)
                            disableEditText(pressing_flow_edit_text)
                            inputSet?.remove(pressing_force_edit_text)
                            inputSet?.remove(pressure_pressing_edit_text)
                            inputSet?.remove(pressing_flow_edit_text)
                        }
                        calculateResult()
                    } else {
                        calculateResult()
                        if (inputSet?.contains(pressing_force_edit_text) == false && inputSet?.contains(pressure_pressing_edit_text) == false) {
                            enableEditText(pressing_force_edit_text)
                            enableEditText(pressure_pressing_edit_text)
                        }
                        if (inputSet?.contains(speed_pressing_edit_text) == false && inputSet?.contains(pressing_flow_edit_text) == false) {
                            enableEditText(speed_pressing_edit_text)
                            enableEditText(pressing_flow_edit_text)
                        }
                        if (inputSet?.contains(pressing_force_edit_text) == false && inputSet?.contains(pressure_pressing_edit_text) == false) {
                            inputSet?.add(pressing_force_edit_text)
                            inputSet?.add(pressure_pressing_edit_text)
                        }
                        if (inputSet?.contains(speed_pressing_edit_text) == false && inputSet?.contains(pressing_flow_edit_text) == false) {
                            inputSet?.add(speed_pressing_edit_text)
                            inputSet?.add(pressing_flow_edit_text)
                        }
                    }
                }

                bore_edit_text -> {
                    calculateResult()
                }
                rod_edit_text -> {
                    calculateResult()
                }
                stroke_edit_text -> {
                    calculateResult()
                }
                else -> {
                    Log.d(TAG, "DO NOTHING")
                }
            }
        }
    }

    class EditTextInputWatcher(val mEditText: EditText, val mCallBack: EditTextChangeListener):
        TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            mCallBack.onTextChangeComplete(mEditText)
        }
    }

    private fun disableEditText(editText: EditText) {
        editText.isFocusable = false
        editText.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.edittext_box_style_grey,
            null
        )
    }

    private fun enableEditText(editText: EditText) {
        editText.isFocusableInTouchMode = true
        editText.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.edittext_box_style,
            null
        )
    }

    private fun enableButton(button: Button) {
        button.isClickable = true
        button.background =  ResourcesCompat.getDrawable(
            resources,
            R.drawable.button_drawable,
            null
        )
    }

    private fun disableButton(button: Button) {
        button.isClickable = false
        button.background =  ResourcesCompat.getDrawable(
            resources,
            R.drawable.button_drawable_grey,
            null
        )
    }

    private fun setText(editText: EditText, value: String) {
        if (inputSet?.contains(editText) == false) {
            editText.setText(value)
        }
    }

    private fun calculateResult() {

        inputSet?.let { inputSet ->
            val numCylinder = cyclinder_edit_text.text.toString()
            val efficiency = text_pump_efficiency.text.toString()
            val boreString =
                if (!inputSet.contains(bore_edit_text)) "" else bore_edit_text.text.toString()
            val boreUnit = bore_spinner.selectedItem as UNIT
            val rodString =
                if (!inputSet.contains(rod_edit_text)) "" else rod_edit_text.text.toString()
            val rodUnit = rod_spinner.selectedItem as UNIT
            val strokeString =
                if (!inputSet.contains(stroke_edit_text)) "" else stroke_edit_text.text.toString()
            val strokeUnit = stroke_spinner.selectedItem as UNIT
            val pressureUpString =
                if (!inputSet.contains(up_pressure_edit_text)) "" else up_pressure_edit_text.text.toString()
            val pressureDownString =
                if (!inputSet.contains(down_pressure_edit_text)) "" else down_pressure_edit_text.text.toString()
            val pressurePressingString =
                if (!inputSet.contains(pressure_pressing_edit_text)) "" else pressure_pressing_edit_text.text.toString()
            val pressureUnit = pressure_spinner.selectedItem as UNIT
            val forceUpString =
                if (!inputSet.contains(up_force_edit_text)) "" else up_force_edit_text.text.toString()
            val forceDownString =
                if (!inputSet.contains(down_force_edit_text)) "" else down_force_edit_text.text.toString()
            val forcePressingString =
                if (!inputSet.contains(pressing_force_edit_text)) "" else pressing_force_edit_text.text.toString()
            val forceUnit = force_spinner.selectedItem as UNIT
            var flowUpString =
                if (!inputSet.contains(up_flow_edit_text)) "" else up_flow_edit_text.text.toString()
            var flowDownString =
                if (!inputSet.contains(down_flow_edit_text)) "" else down_flow_edit_text.text.toString()
            var flowPressingString =
                if (!inputSet.contains(pressing_flow_edit_text)) "" else pressing_flow_edit_text.text.toString()
            val flowUnit = flow_spinner.selectedItem as UNIT
            val speedUpString =
                if (!inputSet.contains(up_speed_edit_text)) "" else up_speed_edit_text.text.toString()
            val speedDownString =
                if (!inputSet.contains(speed_down_edit_text)) "" else speed_down_edit_text.text.toString()
            val speedPressingString =
                if (!inputSet.contains(speed_pressing_edit_text)) "" else speed_pressing_edit_text.text.toString()
            val speedUnit = speed_spinner.selectedItem as UNIT
            val motorUpString =
                if (!inputSet.contains(up_motor_edit_text)) "" else up_motor_edit_text.text.toString()
            val motorDownString =
                if (!inputSet.contains(down_motor_edit_text)) "" else down_motor_edit_text.text.toString()
            val motorPressingString =
                if (!inputSet.contains(pressing_motor_edit_text)) "" else pressing_motor_edit_text.text.toString()
            val motorUnit = motor_spinner.selectedItem as UNIT

            val tankUnit = tank_capacity_spinner.selectedItem as UNIT
            val volOilStrokeUnit = oil_stroke_spinner.selectedItem as UNIT

//        updating the reset share and copy button
            if (FormulaUtil.isValid(boreString) && FormulaUtil.isValid(rodString) && FormulaUtil.isValid(
                    strokeString
                )
            ) {
                save_button.isClickable = true
                save_button.background = resources.getDrawable(R.drawable.button_drawable, null)
                share_button.isClickable = true
                share_button.background = resources.getDrawable(R.drawable.button_drawable, null)
            } else {
                save_button.isClickable = false
                save_button.background = resources.getDrawable(R.drawable.button_drawable_grey, null)
                save_button.isClickable = false
                save_button.background = resources.getDrawable(R.drawable.button_drawable_grey, null)
            }

            setText(
                up_force_edit_text, FormulaUtil.calculatePowerpackForceSide(
                    numCylinder,
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    pressureUpString,
                    pressureUnit,
                    flowUpString,
                    flowUnit,
                    speedUpString,
                    speedUnit,
                    motorUpString,
                    motorUnit,
                    forceUnit
                )
            )

            setText(
                down_force_edit_text, FormulaUtil.calculatePowerpackForceSide(
                    numCylinder,
                    boreString,
                    boreUnit,
                    pressureDownString,
                    pressureUnit,
                    flowDownString,
                    flowUnit,
                    speedDownString,
                    speedUnit,
                    motorDownString,
                    motorUnit,
                    forceUnit
                )
            )

            setText(
                pressing_force_edit_text, FormulaUtil.calculatePowerpackForceSide(
                    numCylinder,
                    boreString,
                    boreUnit,
                    pressurePressingString,
                    pressureUnit,
                    flowPressingString,
                    flowUnit,
                    speedPressingString,
                    speedUnit,
                    motorPressingString,
                    motorUnit,
                    forceUnit
                )
            )

            setText(
                up_pressure_edit_text, FormulaUtil.calculatePowerpackPressureSide(
                    numCylinder,
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    forceUpString,
                    forceUnit,
                    flowUpString,
                    flowUnit,
                    speedUpString,
                    speedUnit,
                    motorUpString,
                    motorUnit,
                    pressureUnit
                )
            )

            setText(
                down_pressure_edit_text, FormulaUtil.calculatePowerpackPressureSide(
                    numCylinder,
                    boreString,
                    boreUnit,
                    forceDownString,
                    forceUnit,
                    flowDownString,
                    flowUnit,
                    speedDownString,
                    speedUnit,
                    motorDownString,
                    motorUnit,
                    pressureUnit
                )
            )

            setText(
                pressure_pressing_edit_text, FormulaUtil.calculatePowerpackPressureSide(
                    numCylinder,
                    boreString,
                    boreUnit,
                    forcePressingString,
                    forceUnit,
                    flowPressingString,
                    flowUnit,
                    speedPressingString,
                    speedUnit,
                    motorPressingString,
                    motorUnit,
                    pressureUnit
                )
            )

            setText(
                up_flow_edit_text, FormulaUtil.calculatePowerpackFlow(
                    numCylinder,
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    speedUpString,
                    speedUnit,
                    forceUpString,
                    forceUnit,
                    pressureUpString,
                    pressureUnit,
                    motorUpString,
                    motorUnit,
                    flowUnit
                )
            )
            setText(
                down_flow_edit_text, FormulaUtil.calculatePowerpackFlow(
                    numCylinder,
                    boreString,
                    boreUnit,
                    speedDownString,
                    speedUnit,
                    forceDownString,
                    forceUnit,
                    pressureDownString,
                    pressureUnit,
                    motorDownString,
                    motorUnit,
                    flowUnit
                )
            )
            setText(
                pressing_flow_edit_text, FormulaUtil.calculatePowerpackFlow(
                    numCylinder,
                    boreString,
                    boreUnit,
                    speedPressingString,
                    speedUnit,
                    forcePressingString,
                    forceUnit,
                    pressurePressingString,
                    pressureUnit,
                    motorPressingString,
                    motorUnit,
                    flowUnit
                )
            )

            setText(
                up_speed_edit_text, FormulaUtil.calculatePowerpackSpeed(
                    numCylinder,
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    flowUpString,
                    flowUnit,
                    forceUpString,
                    forceUnit,
                    pressureUpString,
                    pressureUnit,
                    motorUpString,
                    motorUnit,
                    flowUnit
                )
            )
            setText(
                speed_down_edit_text, FormulaUtil.calculatePowerpackSpeed(
                    numCylinder,
                    boreString,
                    boreUnit,
                    flowDownString,
                    flowUnit,
                    forceDownString,
                    forceUnit,
                    pressureDownString,
                    pressureUnit,
                    motorDownString,
                    motorUnit,
                    flowUnit
                )
            )
            setText(
                speed_pressing_edit_text, FormulaUtil.calculatePowerpackSpeed(
                    numCylinder,
                    boreString,
                    boreUnit,
                    flowPressingString,
                    flowUnit,
                    forcePressingString,
                    forceUnit,
                    pressurePressingString,
                    pressureUnit,
                    motorPressingString,
                    motorUnit,
                    flowUnit
                )
            )
            setText(
                up_motor_edit_text, FormulaUtil.calculatePowerpackMotor(
                    numCylinder,
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    pressureUpString,
                    pressureUnit,
                    forceUpString,
                    forceUnit,
                    flowUpString,
                    flowUnit,
                    speedUpString,
                    speedUnit,
                    motorUnit
                )
            )
            setText(
                down_motor_edit_text, FormulaUtil.calculatePowerpackMotor(
                    numCylinder,
                    boreString,
                    boreUnit,
                    pressureDownString,
                    pressureUnit,
                    forceDownString,
                    forceUnit,
                    flowDownString,
                    flowUnit,
                    speedDownString,
                    speedUnit,
                    motorUnit
                )
            )
            setText(
                pressing_motor_edit_text, FormulaUtil.calculatePowerpackMotor(
                    numCylinder,
                    boreString,
                    boreUnit,
                    pressurePressingString,
                    pressureUnit,
                    forcePressingString,
                    forceUnit,
                    flowPressingString,
                    flowUnit,
                    speedPressingString,
                    speedUnit,
                    motorUnit
                )
            )

            setText(
                oil_stroke_edit_text, FormulaUtil.calculatePowerpackOilStroke(
                    numCylinder,
                    boreString,
                    boreUnit,
                    strokeString,
                    strokeUnit,
                    volOilStrokeUnit
                )
            )

            flowUpString = up_flow_edit_text.text.toString()
            flowDownString = down_flow_edit_text.text.toString()
            flowPressingString =  pressing_flow_edit_text.text.toString()


            setText(
                tank_capacity_edit_text, FormulaUtil.calculateTankCapacity(
                    flowUpString,
                    flowDownString,
                    flowPressingString,
                    flowUnit,
                    tankUnit
                )
            )
        }
    }

    private val spinnerItemSelectListener  = object: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val selectedItem = parent.getItemAtPosition(position) as UNIT
            Log.d(TAG, "selectedItem ${selectedItem.unit}")
            if (parent.id == bore_spinner.id) {
                Log.d(TAG, "true")
            }

            mSpinnerEditTextMap?.let {

                for (editText in it[parent.id]!!) {
                    Log.d(TAG, "EditText: ${editText.text}")
                    if (editText.text.isNotEmpty()) {
                        val d: Double = (editText.text.toString()).toDouble()
                        mSpinnerIdCurrentUnitMap?.let {
                            val res = ConversionUtil.convertUnit(
                                d,
                                it[parent.id]!!,
                                selectedItem
                            )
                            val dec = DecimalFormat("#.#####")
                            editText.setText(dec.format(res).toString())
                        }
                    } else {
                        mPowerpackData?.let {
                            updateEditText(it, editText)
                        }
                    }
                }
            }

            mSpinnerIdCurrentUnitMap?.put(parent.id, selectedItem)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_powerpack, container, false)
        return root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PowerpackFragmentArgs by navArgs()
        mPowerpackData = args.powerpackData

        val sViews = arrayOf(
            bore_spinner,
            rod_spinner,
            stroke_spinner,
            force_spinner,
            speed_spinner,
            pressure_spinner,
            flow_spinner,
            motor_spinner,
            oil_stroke_spinner,
            tank_capacity_spinner
        )
        val editTextViews = arrayOf(
            bore_edit_text,
            rod_edit_text,
            stroke_edit_text,
            up_force_edit_text,
            down_force_edit_text,
            pressing_force_edit_text,
            up_pressure_edit_text,
            down_pressure_edit_text,
            pressure_pressing_edit_text,
            up_speed_edit_text,
            speed_down_edit_text,
            speed_pressing_edit_text,
            up_flow_edit_text,
            down_flow_edit_text,
            pressing_flow_edit_text,
            up_motor_edit_text,
            down_motor_edit_text,
            pressing_motor_edit_text,
            oil_stroke_edit_text,
            tank_capacity_edit_text
        )

        inputSet = mutableSetOf(
            bore_edit_text,
            rod_edit_text,
            stroke_edit_text,
            up_force_edit_text,
            down_force_edit_text,
            pressing_force_edit_text,
            up_pressure_edit_text,
            down_pressure_edit_text,
            pressure_pressing_edit_text,
            up_speed_edit_text,
            speed_down_edit_text,
            speed_pressing_edit_text,
            up_flow_edit_text,
            down_flow_edit_text,
            pressing_flow_edit_text,
            up_motor_edit_text,
            down_motor_edit_text,
            pressing_motor_edit_text
        )

        plus.setOnClickListener {
            var nCyl =  cyclinder_edit_text?.text.toString().toInt()
            nCyl += 1
            cyclinder_edit_text.setText(nCyl.toString())
            calculateResult()
        }

        minus.setOnClickListener {
            var nCyl =  cyclinder_edit_text?.text.toString().toInt()
            if (nCyl > 1) {
                nCyl -= 1
            }
            cyclinder_edit_text.setText(nCyl.toString())
            calculateResult()
        }

        mEditTextTextWatcherMap = mutableMapOf()
        for(v in editTextViews) {
            v.filters = arrayOf<InputFilter>(DecimalDigitInputFilter(5, 5))
            val editTextInputWatcher = EditTextInputWatcher(v, mEditTextChangeListener)
            mEditTextTextWatcherMap?.let {
                it[v] = editTextInputWatcher
            }
            v.addTextChangedListener(editTextInputWatcher)
        }

        mSpinnerEditTextMap = mutableMapOf(
            bore_spinner.id to arrayOf(bore_edit_text),
            rod_spinner.id to arrayOf(rod_edit_text),
            stroke_spinner.id to arrayOf(stroke_edit_text),
            force_spinner.id to arrayOf(up_force_edit_text, down_force_edit_text, pressing_force_edit_text),
            speed_spinner.id to arrayOf(up_speed_edit_text, speed_down_edit_text, speed_pressing_edit_text),
            pressure_spinner.id to arrayOf(up_pressure_edit_text, down_pressure_edit_text, pressure_pressing_edit_text),
            flow_spinner.id to arrayOf(up_flow_edit_text, up_flow_edit_text, pressing_flow_edit_text),
            motor_spinner.id to arrayOf(up_motor_edit_text, down_motor_edit_text, pressing_motor_edit_text),
            oil_stroke_spinner.id to arrayOf(oil_stroke_edit_text),
            tank_capacity_spinner.id to arrayOf(tank_capacity_edit_text)
        )

        mSpinnerIdCurrentUnitMap = mutableMapOf(
            bore_spinner.id to LENGTH.MM,
            rod_spinner.id to LENGTH.MM,
            stroke_spinner.id to LENGTH.MM,
            force_spinner.id to FORCE.TON,
            speed_spinner.id to SPEED.MM_SEC,
            pressure_spinner.id to PRESSURE.BAR,
            flow_spinner.id to FLOW.L_MIN,
            motor_spinner.id to ENERGY.HP,
            oil_stroke_spinner.id to VOLUME.LITRE,
            tank_capacity_spinner.id to VOLUME.LITRE
        )

        for (i in sViews.indices) {
            CustomSpinnerAdapter(
                mContext,
                R.layout.custom_spinner_item,
                spinnerDataArray[i]
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                sViews[i].adapter = adapter
            }
            sViews[i].onItemSelectedListener = spinnerItemSelectListener
        }

        mPowerpackData?.let {powerpackData ->

            bore_spinner.setSelection(powerpackData.mBoreDiameterUnit)
            rod_spinner.setSelection(powerpackData.mRodDiameterUnit)
            stroke_spinner.setSelection(powerpackData.mStrokeUnit)
            pressure_spinner.setSelection(powerpackData.mPressureUnit)
            force_spinner.setSelection(powerpackData.mForceUnit)
            speed_spinner.setSelection(powerpackData.mSpeedUnit)
            flow_spinner.setSelection(powerpackData.mFlowUnit)

            cyclinder_edit_text.text = powerpackData.mNumCylinder.toString()
        }

        enableButton(reset_button)
        reset_button.setOnClickListener{
            for (editText in editTextViews) {
                if (inputSet?.contains(editText) == true) {
                    editText.setText("")
                }
            }
        }


        save_button.setOnClickListener{
            val builder = AlertDialog.Builder(mContext)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.custom_dialog, null)
            val projectEditText : EditText = view.findViewById(R.id.project_name)
            builder.setView(view)
            builder.setView(view)
                .setNegativeButton(R.string.cancel_text,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                .setPositiveButton(R.string.save_project,
                    DialogInterface.OnClickListener { dialog, id ->
                        mSaveProjectListener.onSave(projectEditText.text.toString())
//                        Toast.makeText(mContext, "Not Implemented!" , Toast.LENGTH_SHORT).show()
                    })
            val alertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

            projectEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable?) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = !TextUtils.isEmpty(s)
                }

            })
        }
    }

    private fun updateEditText(powerpackData: Powerpack, editText: EditText) {
        when (editText) {
            bore_edit_text -> if (powerpackData.mBoreDiameter.isNotEmpty()) {
                bore_edit_text.setText(powerpackData.mBoreDiameter)
//                powerpackData.mBoreDiameter = ""
            }
            rod_edit_text -> if (powerpackData.mRodDiameter.isNotEmpty()) {
                rod_edit_text.setText(powerpackData.mRodDiameter)
//                powerpackData.mRodDiameter = ""
            }
            stroke_edit_text -> if (powerpackData.mStroke.isNotEmpty()) {
                stroke_edit_text.setText(powerpackData.mStroke)
//                powerpackData.mStroke = ""
            }
            up_force_edit_text -> if (powerpackData.mUpForce.isNotEmpty()) {
                up_force_edit_text.setText(powerpackData.mUpForce)
//                powerpackData.mUpForce = ""
            }
            down_force_edit_text -> if (powerpackData.mDownForce.isNotEmpty()) {
                down_force_edit_text.setText(powerpackData.mDownForce)
//                powerpackData.mDownForce = ""
            }
            pressing_force_edit_text -> if (powerpackData.mPressingForce.isNotEmpty()) {
                pressing_force_edit_text.setText(powerpackData.mPressingForce)
//                powerpackData.mPressingForce = ""
            }
            up_speed_edit_text -> if (powerpackData.mUpSpeed.isNotEmpty()) {
                up_speed_edit_text.setText(powerpackData.mUpSpeed)
//                powerpackData.mUpSpeed = ""
            }
            speed_down_edit_text -> if (powerpackData.mDownSpeed.isNotEmpty()) {
                speed_down_edit_text.setText(powerpackData.mDownSpeed)
//                powerpackData.mDownSpeed = ""
            }
            speed_pressing_edit_text -> if (powerpackData.mPressingSpeed.isNotEmpty()) {
                speed_pressing_edit_text.setText(powerpackData.mPressingSpeed)
//                powerpackData.mPressingSpeed = ""
            }
            up_pressure_edit_text -> if (powerpackData.mUpPressure.isNotEmpty()) {
                up_pressure_edit_text.setText(powerpackData.mUpPressure)
//                powerpackData.mUpPressure = ""
            }
            down_pressure_edit_text -> if (powerpackData.mDownPressure.isNotEmpty()) {
                down_pressure_edit_text.setText(powerpackData.mDownPressure)
//                powerpackData.mDownPressure = ""
            }
            pressure_pressing_edit_text -> if (powerpackData.mPressingPressure.isNotEmpty()) {
                pressure_pressing_edit_text.setText(powerpackData.mPressingPressure)
//                powerpackData.mPressingPressure = ""
            }
            up_flow_edit_text -> if (powerpackData.mUpFlow.isNotEmpty()) {
                up_flow_edit_text.setText(powerpackData.mUpFlow)
//                powerpackData.mUpFlow = ""
            }
            down_flow_edit_text -> if (powerpackData.mDownFlow.isNotEmpty()) {
                down_flow_edit_text.setText(powerpackData.mDownFlow)
//                powerpackData.mDownFlow = ""
            }
            pressing_flow_edit_text -> if (powerpackData.mPressingFlow.isNotEmpty()) {
                pressing_flow_edit_text.setText(powerpackData.mPressingFlow)
//                powerpackData.mPressingFlow = ""
            }
            up_motor_edit_text -> if (powerpackData.mUpMotor.isNotEmpty()) {
                up_motor_edit_text.setText(powerpackData.mUpMotor)
//                powerpackData.mUpMotor = ""
            }
            down_motor_edit_text -> if (powerpackData.mDownMotor.isNotEmpty()) {
                down_motor_edit_text.setText(powerpackData.mDownMotor)
//                powerpackData.mDownMotor = ""
            }
            pressing_motor_edit_text -> if (powerpackData.mPressingMotor.isNotEmpty()) {
                pressing_motor_edit_text.setText(powerpackData.mPressingMotor)
//                powerpackData.mPressingMotor = ""
            }

        }
    }
}
