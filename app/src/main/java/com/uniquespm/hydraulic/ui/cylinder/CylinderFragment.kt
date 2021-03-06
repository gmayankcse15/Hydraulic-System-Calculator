package com.uniquespm.hydraulic.ui.cylinder

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
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
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.common.CustomSpinnerAdapter
import com.uniquespm.hydraulic.common.DecimalDigitInputFilter
import com.uniquespm.hydraulic.common.SaveProjectCallback
import com.uniquespm.hydraulic.model.Cylinder
import com.uniquespm.hydraulic.model.DataRepository
import com.uniquespm.hydraulic.util.*
import com.uniquespm.hydraulic.util.FormulaUtil.Companion.getValidData
import com.uniquespm.hydraulic.util.FormulaUtil.Companion.isValid
import kotlinx.android.synthetic.main.fragment_cylinder.*
import kotlinx.android.synthetic.main.fragment_cylinder.bore_edit_text
import kotlinx.android.synthetic.main.fragment_cylinder.bore_spinner
import kotlinx.android.synthetic.main.fragment_cylinder.force_spinner
import kotlinx.android.synthetic.main.fragment_cylinder.pressure_spinner
import kotlinx.android.synthetic.main.fragment_cylinder.reset_button
import kotlinx.android.synthetic.main.fragment_cylinder.rod_edit_text
import kotlinx.android.synthetic.main.fragment_cylinder.rod_spinner
import kotlinx.android.synthetic.main.fragment_cylinder.save_button
import kotlinx.android.synthetic.main.fragment_cylinder.share_button
import kotlinx.android.synthetic.main.fragment_cylinder.stroke_edit_text
import kotlinx.android.synthetic.main.fragment_cylinder.stroke_spinner


class CylinderFragment : Fragment() {

    companion object {
        const val TAG = "CylinderFragment"
    }

    private var mCylinderData: Cylinder? = null
    private lateinit var cylinderViewModel: CylinderViewModel
    private lateinit var mContext: Context
    private val unitLength : Array<UNIT> = arrayOf(LENGTH.MM, LENGTH.CM, LENGTH.INCH)
    private val unitPressure : Array<UNIT> = arrayOf(PRESSURE.BAR, PRESSURE.PSI, PRESSURE.KGCM2)
    private val unitArea : Array<UNIT> = arrayOf(AREA.MM2, AREA.M2, AREA.FT2)
    private val unitVolume : Array<UNIT> = arrayOf(VOLUME.LITRE)
    private val unitForce : Array<UNIT> = arrayOf(FORCE.TON, FORCE.NEWTON)

    private val spinnerDataArray = arrayOf(
        unitLength,
        unitLength,
        unitLength,
        unitPressure,
        unitArea,
        unitVolume,
        unitForce
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
            val cylinder = Cylinder(
                projectName,
                Constants.CYLINDER,
                getValidData(bore_edit_text, inputSet),
                bore_spinner.selectedItemPosition,
                getValidData(rod_edit_text, inputSet),
                rod_spinner.selectedItemPosition,
                getValidData(stroke_edit_text, inputSet),
                stroke_spinner.selectedItemPosition,
                getValidData(pressure_edit_text, inputSet),
                pressure_spinner.selectedItemPosition,
                getValidData(area__bore_side_edit_text, inputSet),
                getValidData(area_edit_text, inputSet),
                area_spinner.selectedItemPosition,
                getValidData(volume_bore_side__edit_text, inputSet),
                getValidData(volume_edit_text, inputSet),
                volume_spinner.selectedItemPosition,
                getValidData(force_bore_side_edit_text, inputSet),
                getValidData(force_edit_text, inputSet),
                force_spinner.selectedItemPosition
                )
            Log.d(TAG, "Cylinder $cylinder")
            mDataRepository?.insertCylinder(cylinder)
        }

    }

    private val mEditTextChangeListener = object : EditTextChangeListener {
        override fun onTextChangeComplete(editText: EditText) {
            if (inputSet?.contains(editText) == false) {
                return
            }
            when(editText) {
                pressure_edit_text -> {
                    if (pressure_edit_text.text.isNotEmpty()) {
                        disableEditText(force_edit_text)
                        disableEditText(force_bore_side_edit_text)
                        inputSet?.remove(force_edit_text)
                        inputSet?.remove(force_bore_side_edit_text)
                        calculateResult()
                    } else {
                        enableEditText(force_edit_text)
                        enableEditText(force_bore_side_edit_text)
                        calculateResult()
                        inputSet?.add(force_edit_text)
                        inputSet?.add(force_bore_side_edit_text)
                    }
                }
                force_edit_text -> {
                    if (force_edit_text.text.isNotEmpty()) {
                        disableEditText(force_bore_side_edit_text)
                        disableEditText(pressure_edit_text)
                        inputSet?.remove(force_bore_side_edit_text)
                        inputSet?.remove(pressure_edit_text)
                        calculateResult()
                    } else {
                        enableEditText(force_bore_side_edit_text)
                        enableEditText(pressure_edit_text)
                        calculateResult()
                        inputSet?.add(force_bore_side_edit_text)
                        inputSet?.add(pressure_edit_text)
                    }
                }
                force_bore_side_edit_text -> {
                    if (force_bore_side_edit_text.text.isNotEmpty()) {
                        disableEditText(force_edit_text)
                        disableEditText(pressure_edit_text)
                        inputSet?.remove(force_edit_text)
                        inputSet?.remove(pressure_edit_text)
                        calculateResult()
                    } else {
                        enableEditText(force_edit_text)
                        enableEditText(pressure_edit_text)
                        calculateResult()
                        inputSet?.add(force_edit_text)
                        inputSet?.add(pressure_edit_text)
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

    class EditTextInputWatcher(val mEditText: EditText, val mCallBack: EditTextChangeListener): TextWatcher {
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
            val boreString =
                if (!inputSet.contains(bore_edit_text)) "" else bore_edit_text.text.toString()
            val boreUnit = bore_spinner.selectedItem as UNIT
            val rodString =
                if (!inputSet.contains(rod_edit_text)) "" else rod_edit_text.text.toString()
            val rodUnit = rod_spinner.selectedItem as UNIT
            val strokeString =
                if (!inputSet.contains(stroke_edit_text)) "" else stroke_edit_text.text.toString()
            val strokeUnit = stroke_spinner.selectedItem as UNIT
            val pressureString =
                if (!inputSet.contains(pressure_edit_text)) "" else pressure_edit_text.text.toString()
            val pressureUnit = pressure_spinner.selectedItem as UNIT
            val forceRodString =
                if (!inputSet.contains(force_edit_text)) "" else force_edit_text.text.toString()
            val forceBoreString =
                if (!inputSet.contains(force_bore_side_edit_text)) "" else force_bore_side_edit_text.text.toString()
            val forceUnit = force_spinner.selectedItem as UNIT
            val areaUnit = area_spinner.selectedItem as UNIT
            val volumeUnit = volume_spinner.selectedItem as UNIT
//        updating the reset share and copy button
        if (isValid(boreString) && isValid(rodString) && isValid(strokeString)) {
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
                force_bore_side_edit_text, FormulaUtil.calculateForceBoreSide(
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    pressureString,
                    pressureUnit,
                    forceRodString,
                    forceUnit,
                    forceUnit
                )
            )
            setText(
                pressure_edit_text, FormulaUtil.calculatePressure(
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    forceRodString,
                    forceBoreString,
                    forceUnit,
                    pressureUnit
                    )
            )

            setText(
                force_edit_text, FormulaUtil.calculateForceRodSide(
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    pressureString,
                    pressureUnit,
                    forceBoreString,
                    forceUnit,
                    forceUnit
                )
            )

            setText(
                area__bore_side_edit_text, FormulaUtil.calculateAreaBoreSide(
                    boreString,
                    boreUnit,
                    areaUnit
                )
            )

            setText(
                area_edit_text, FormulaUtil.calculateAreaRodSide(
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    areaUnit
                )
            )

            setText(
                volume_bore_side__edit_text, FormulaUtil.calculateVolumeBoreSide(
                    boreString,
                    boreUnit,
                    strokeString,
                    strokeUnit,
                    volumeUnit
                )
            )

            setText(
                volume_edit_text, FormulaUtil.calculateVolumeRodSide(
                    boreString,
                    boreUnit,
                    rodString,
                    rodUnit,
                    strokeString,
                    strokeUnit,
                    volumeUnit
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
                            val dec = DecimalFormat("#.##")
                            editText.setText(dec.format(res).toString())
                        }
                    } else {
                        mCylinderData?.let {
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

        val args: CylinderFragmentArgs by navArgs()
        mCylinderData = args.cylinderData

        if (mCylinderData == null) {
            Log.d(TAG, "Cylinder Data is null")
        } else {
            Log.d(TAG, "Cylinder Data is not null")
        }

        val sViews = arrayOf(
            bore_spinner,
            rod_spinner,
            stroke_spinner,
            pressure_spinner,
            area_spinner,
            volume_spinner,
            force_spinner
        )
        val editTextViews = arrayOf(
            bore_edit_text,
            rod_edit_text,
            stroke_edit_text,
            pressure_edit_text,
            area_edit_text,
            area__bore_side_edit_text,
            volume_edit_text,
            volume_bore_side__edit_text,
            force_edit_text,
            force_bore_side_edit_text
        )

        inputSet = mutableSetOf(
            bore_edit_text,
            rod_edit_text,
            stroke_edit_text,
            pressure_edit_text,
            force_edit_text,
            force_bore_side_edit_text
        )

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
            pressure_spinner.id to arrayOf(pressure_edit_text),
            area_spinner.id to arrayOf(area_edit_text, area__bore_side_edit_text),
            volume_spinner.id to arrayOf(volume_edit_text, volume_bore_side__edit_text),
            force_spinner.id to arrayOf(force_edit_text, force_bore_side_edit_text)
        )

        mSpinnerIdCurrentUnitMap = mutableMapOf(
            bore_spinner.id to unitLength[0],
            rod_spinner.id to unitLength[0],
            stroke_spinner.id to unitLength[0],
            pressure_spinner.id to unitPressure[0],
            area_spinner.id to unitArea[0],
            volume_spinner.id to unitVolume[0],
            force_spinner.id to unitForce[0]
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

        mCylinderData?.let {cylinderData ->

            bore_spinner.setSelection(cylinderData.mBoreDiameterUnit)
            rod_spinner.setSelection(cylinderData.mRodDiameterUnit)
            stroke_spinner.setSelection(cylinderData.mStrokeUnit)
            pressure_spinner.setSelection(cylinderData.mPressureUnit)
            area_spinner.setSelection(cylinderData.mAreaSideUnit)
            volume_spinner.setSelection(cylinderData.mVolumeSideUnit)
            force_spinner.setSelection(cylinderData.mForceSideUnit)

//            updateEditText(cylinderData)
        }

        enableButton(reset_button)
        reset_button.setOnClickListener{
            for (editText in editTextViews) {
                if (inputSet?.contains(editText) == true) {
                    editText.setText("")
                }
            }
        }

        fun constructData() : String {
            var s: StringBuilder = StringBuilder()
            s.append(text_bore_diameter.text.toString()).append(" : ")
                .append(bore_edit_text.text.toString())
                .append((bore_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_rod_diameter.text.toString()).append(" : ")
                .append(rod_edit_text.text.toString())
                .append((rod_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_stroke.text.toString()).append(" : ")
                .append(stroke_edit_text.text.toString())
                .append((stroke_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_pressure.text.toString()).append(" : ").append(pressure_edit_text.text.toString()).append((pressure_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_bore_side.text.toString()).append('\n')
            s.append(text_area.text.toString()).append(" : ").append(area__bore_side_edit_text.text.toString()).append((area_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_volume.text.toString()).append(" : ").append(volume_bore_side__edit_text.text.toString()).append((volume_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_force.text.toString()).append(" : ").append(force_bore_side_edit_text.text.toString()).append((force_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_rod_side.text.toString()).append('\n')
            s.append(text_area.text.toString()).append(" : ").append(area_edit_text.text.toString()).append((area_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_volume.text.toString()).append(" : ").append(volume_edit_text.text.toString()).append((volume_spinner.selectedItem as UNIT).unit).append('\n')
            s.append(text_force.text.toString()).append(" : ").append(force_edit_text.text.toString()).append((force_spinner.selectedItem as UNIT).unit).append('\n')
            return s.toString()
        }



            share_button.setOnClickListener {
            val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip : ClipData = ClipData.newPlainText("simple text", constructData())
            clipboard.setPrimaryClip(clip)
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

    private fun updateEditText(cylinderData: Cylinder, editText: EditText) {
        when (editText) {
            bore_edit_text -> if (cylinderData.mBoreDiameter.isNotEmpty()) {
                bore_edit_text.setText(cylinderData.mBoreDiameter)
//                cylinderData.mBoreDiameter = ""
            }
            rod_edit_text -> if (cylinderData.mRodDiameter.isNotEmpty()) {
                rod_edit_text.setText(cylinderData.mRodDiameter)
//                cylinderData.mRodDiameter = ""
            }
            stroke_edit_text -> if (cylinderData.mStroke.isNotEmpty()) {
                stroke_edit_text.setText(cylinderData.mStroke)
//                cylinderData.mStroke = ""
            }
            pressure_edit_text -> if (cylinderData.mPressure.isNotEmpty()) {
                pressure_edit_text.setText(cylinderData.mPressure)
//                cylinderData.mPressure = ""
            }
            force_bore_side_edit_text -> if (cylinderData.mForceBoreSide.isNotEmpty()) {
                force_bore_side_edit_text.setText(cylinderData.mForceBoreSide)
//                cylinderData.mForceBoreSide = ""
            }
            force_edit_text -> if (cylinderData.mForceRodSide.isNotEmpty()) {
                force_edit_text.setText(cylinderData.mForceRodSide)
//                cylinderData.mForceRodSide = ""
            }
        }
    }
}
