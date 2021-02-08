package com.uniquespm.hydraulic.ui.cylinder

import android.content.Context
import android.content.res.Configuration
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.FOCUSABLE
import android.view.View.NOT_FOCUSABLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.common.CustomSpinnerAdapter
import com.uniquespm.hydraulic.common.DecimalDigitInputFilter
import com.uniquespm.hydraulic.util.*
import kotlinx.android.synthetic.main.fragment_cylinder.*

class CylinderFragment : Fragment() {

    companion object {
        const val TAG = "CylinderFragment"
    }

    private lateinit var cylinderViewModel: CylinderViewModel
    private lateinit var mContext: Context
    private val unitLength : Array<UNIT> = arrayOf(LENGTH.MM, LENGTH.CM, LENGTH.INCH)
    private val unitPressure : Array<UNIT> = arrayOf(PRESSURE.BAR, PRESSURE.PSI, PRESSURE.KGCM2)
    private val unitArea : Array<UNIT> = arrayOf(AREA.MM2, AREA.M2, AREA.FT2)
    private val unitVolume : Array<UNIT> = arrayOf(VOLUME.LITRE)
    private val unitForce : Array<UNIT> = arrayOf(FORCE.TON, FORCE.NEWTON)

    private val spinnerDataArray = arrayOf(unitLength, unitLength, unitLength, unitPressure, unitArea, unitVolume, unitForce)
    private var mSpinnerEditTextMap: MutableMap<Int, Array<EditText>>? = null
    private var mSpinnerIdCurrentUnitMap: MutableMap<Int, UNIT>? = null
    private var mEditTextSpinnerMap: MutableMap<Int, AppCompatSpinner>? = null

    private val editTextInputWatcher= object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            Log.d(TAG, "text")
            updateCylinderParameters()
        }

    }

    private fun updateCylinderParameters() {
        if (pressure_edit_text.text.isNotEmpty()) {
            force_edit_text.focusable = NOT_FOCUSABLE
            force_edit_text.background = resources.getDrawable(R.drawable.edittext_box_style_grey, null)
            force_bore_side_edit_text.focusable = NOT_FOCUSABLE
            force_bore_side_edit_text.background = resources.getDrawable(R.drawable.edittext_box_style_grey, null)
        } else {
            force_edit_text.focusable = FOCUSABLE
            force_edit_text.isClickable = true
            force_edit_text.background = resources.getDrawable(R.drawable.edittext_box_style, null)
            force_bore_side_edit_text.focusable = FOCUSABLE
            force_bore_side_edit_text.background = resources.getDrawable(R.drawable.edittext_box_style, null)
        }

        val boreString = bore_edit_text.text.toString()
        val rodString = rod_edit_text.text.toString()
        val strokeString = stroke_edit_text.text.toString()
        val pressureString = pressure_edit_text.text.toString()

        if (boreString.isNotEmpty() && rodString.isNotEmpty() && strokeString.isNotEmpty()) {
            calculate_button.isClickable = true
            calculate_button.background = resources.getDrawable(R.drawable.button_drawable, null)
            reset_button.isClickable = true
            reset_button.background = resources.getDrawable(R.drawable.button_drawable, null)
        } else {
            calculate_button.isClickable = false
            calculate_button.background = resources.getDrawable(R.drawable.button_drawable_grey, null)
            reset_button.isClickable = false
            reset_button.background = resources.getDrawable(R.drawable.button_drawable_grey, null)
        }

//        // calculate area bore side and rod side
//        if (boreString.isNotEmpty()) {
//            var res = calculateArea(boreString.toDouble())
//            area__bore_side_edit_text.setText(res.toString())
//
//            if (strokeString.isNotEmpty()) {
//                res = calculateVolume(boreString.toDouble(), strokeString.toDouble())
//                volume_edit_text.setText(res.toString())
//            }
//        }
//
//        // calculate area rod side and bore side
//        if (boreString.isNotEmpty() && rodString.isNotEmpty()) {
//            var res = calculateArea(boreString.toDouble(), rodString.toDouble());
//            area__bore_side_edit_text.setText(res.toString())
//
//            if (strokeString.isNotEmpty()) {
//                res = calculateVolume(boreString.toDouble(), rodString.toDouble(), strokeString.toDouble())
//                area_edit_text.setText(res.toString())
//            }
//        }
//
//        //calculate force rod side and bore side
//        if (boreString.isNotEmpty() && pressureString.isNotEmpty()) {
//            var res = calculateForce(boreString.toDouble(), pressureString.toDouble())
//            force_bore_side_edit_text.setText(res.toString())
//
//            if (rodString.isNotEmpty()) {
//                res = calculateForce(boreString.toDouble(), rodString.toDouble(), pressureString.toDouble())
//                force_edit_text.setText(res.toString())
//            }
//        }
    }

    private fun calculateForce(toDouble: Double, toDouble1: Double): Any {
        return 0.0
    }

    private fun calculateForce(toDouble: Double, toDouble1: Double, pressure: Double): Any {
        return 0.0
    }

    private fun calculateVolume(toDouble: Double, toDouble1: Double): Double {
        return 0.0
    }

    private fun calculateVolume(toDouble: Double, toDouble1: Double, stroke: Double): Double {
        return 0.0
    }

    private fun calculateArea(boreDiameter: Double): Double {
        return 0.0
    }

    private fun calculateArea(boreDiameter: Double, rodDiameter: Double): Double {
        return 0.0
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
                            val dec = DecimalFormat("######.#####")
                            editText.setText(dec.format(res).toString())
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
        val root = inflater.inflate(R.layout.fragment_cylinder, container, false)
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

        val sViews = arrayOf(bore_spinner, rod_spinner, stroke_spinner, pressure_spinner, area_spinner, volume_spinner, force_spinner)
        val editTextViews = arrayOf(bore_edit_text, rod_edit_text, stroke_edit_text, pressure_edit_text, area_edit_text, area__bore_side_edit_text, volume_edit_text,
        volume_bore_side__edit_text, force_edit_text, force_bore_side_edit_text)

        for(v in editTextViews) {
            v.filters = arrayOf<InputFilter>(DecimalDigitInputFilter(5, 5))
        }
        bore_edit_text.addTextChangedListener(editTextInputWatcher)
        rod_edit_text.addTextChangedListener(editTextInputWatcher)
        stroke_edit_text.addTextChangedListener(editTextInputWatcher)
        pressure_edit_text.addTextChangedListener(editTextInputWatcher)

        mSpinnerEditTextMap = mutableMapOf(
            bore_spinner.id to arrayOf(bore_edit_text),
            rod_spinner.id to arrayOf(rod_edit_text),
            stroke_spinner.id to arrayOf(stroke_edit_text),
            pressure_spinner.id to arrayOf(pressure_edit_text),
            area_spinner.id to arrayOf(area_edit_text, area__bore_side_edit_text),
            volume_spinner.id to arrayOf(volume_edit_text, volume_bore_side__edit_text),
            force_spinner.id to arrayOf(force_edit_text, force_bore_side_edit_text))

        mSpinnerIdCurrentUnitMap = mutableMapOf(
            bore_spinner.id to LENGTH.MM,
            rod_spinner.id to LENGTH.MM,
            stroke_spinner.id to LENGTH.MM,
            pressure_spinner.id to PRESSURE.BAR,
            area_spinner.id to AREA.M2,
            volume_spinner.id to VOLUME.LITRE,
            force_spinner.id to FORCE.TON
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
        }
}
