package com.uniquespm.hydraulic.ui.convertor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R

class ConvertorListAdapter(val mContext: Context, val array: Array<String>, val unitList: Array<Array<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        holder.setAdapterToSpinner(unitList[position]);
    }

    override fun getItemCount(): Int {
       return array.size
    }

}

class ConvertorViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView
    val convertorDetail: ConstraintLayout
    val convertorItem: ConstraintLayout
    val unitFromSpinner: Spinner
    val unitToSpinner: Spinner

    init {
        textView = view.findViewById(R.id.convertor_title)
        convertorDetail = view.findViewById(R.id.convertor_detail)
        convertorItem = view.findViewById(R.id.convertor_item)
        unitFromSpinner = view.findViewById(R.id.unitFromSpinner)
        unitToSpinner = view.findViewById(R.id.unitToSpinner)
    }

    fun setAdapterToSpinner(array: Array<String>) {
        ArrayAdapter(
            context,
            R.layout.custom_spinner_item,
            array
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            unitFromSpinner.adapter = adapter
            unitToSpinner.adapter = adapter
        }
    }

}
