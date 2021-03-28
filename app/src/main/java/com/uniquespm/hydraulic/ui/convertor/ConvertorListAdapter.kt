package com.uniquespm.hydraulic.ui.convertor

import android.content.Context
import android.icu.text.DecimalFormat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.common.CustomSpinnerAdapter
import com.uniquespm.hydraulic.ui.cylinder.CylinderFragment
import com.uniquespm.hydraulic.util.ConversionUtil
import com.uniquespm.hydraulic.util.UNIT
import kotlinx.android.synthetic.main.fragment_cylinder.*

class ConvertorListAdapter(val mContext: Context, val array: Array<String>, val unitList: ArrayList<Array<UNIT>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "ConvertorListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.convertor_list_item, parent, false)
        return ConvertorViewHolder(mContext, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ConvertorViewHolder).textView.text = array[position]
        holder.itemView.setOnClickListener{
            holder.convertorDetail.visibility = if (holder.convertorDetail.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            holder.convertorItem.background =  if(holder.convertorDetail.visibility == View.VISIBLE) {
                mContext.resources.getDrawable(R.drawable.convertor_item_detail_bg, null)
            }  else {
                mContext.resources.getDrawable(R.drawable.convertor_item_drawable, null)
            }
        }
        holder.initData(unitList[position]);
    }

    override fun getItemCount(): Int {
       return array.size
    }

}

class ConvertorViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.convertor_title)
    val convertorDetail: ConstraintLayout = view.findViewById(R.id.convertor_detail)
    val convertorItem: ConstraintLayout = view.findViewById(R.id.convertor_item)
    val mResetBtn : Button = view.findViewById(R.id.reset_button)
    val unitFromSpinner: Spinner = view.findViewById(R.id.unitFromSpinner)
    val unitToSpinner: Spinner = view.findViewById(R.id.unitToSpinner)
    val fromEditText: EditText = view.findViewById(R.id.unitFrom)
    val toEditText: EditText = view.findViewById(R.id.unitTo)
    var spinnerIdCurrentUnitMap: MutableMap<Int, UNIT>? = null

    fun initData(array: Array<UNIT>) {

        mResetBtn.setOnClickListener {
            fromEditText.setText("")
        }

        fromEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (fromEditText.text.toString().isNotEmpty()) {
                    val d: Double = fromEditText.text.toString().toDouble()
                    spinnerIdCurrentUnitMap?.let {
                        val res = ConversionUtil.convertUnit(
                            d,
                            it[unitFromSpinner.id]!!,
                            it[unitToSpinner.id]!!
                        )
                        val dec = DecimalFormat("#.##")
                        toEditText.setText(dec.format(res).toString())
                    }
                } else {
                    toEditText.setText("")
                }
            }
        })

        spinnerIdCurrentUnitMap = mutableMapOf(
            unitFromSpinner.id to array[0],
            unitToSpinner.id to array[0]
        )
        setAdapterToSpinner(array)
    }

    private val spinnerItemSelectListener  = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val selectedItem = parent.getItemAtPosition(position) as UNIT
            Log.d(CylinderFragment.TAG, "selectedItem ${selectedItem.unit}")
            spinnerIdCurrentUnitMap?.put(parent.id, selectedItem)
            if (fromEditText.text.isNotEmpty()) {
                val d: Double = (fromEditText.text.toString()).toDouble()

                spinnerIdCurrentUnitMap?.let{
                    val res = ConversionUtil.convertUnit(
                        d,
                        it[unitFromSpinner.id]!!,
                        it[unitToSpinner.id]!!
                    )
                    val dec = DecimalFormat("#.##")
                    toEditText.setText(dec.format(res).toString())
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

    fun setAdapterToSpinner(array: Array<UNIT>) {
        CustomSpinnerAdapter(
            context,
            R.layout.custom_spinner_item,
            array
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            unitFromSpinner.adapter = adapter
            unitToSpinner.adapter = adapter
        }
        unitFromSpinner.onItemSelectedListener = spinnerItemSelectListener
        unitToSpinner.onItemSelectedListener = spinnerItemSelectListener
    }

}
