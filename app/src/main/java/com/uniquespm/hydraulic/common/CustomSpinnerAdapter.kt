package com.uniquespm.hydraulic.common

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import androidx.appcompat.widget.AppCompatTextView
import com.uniquespm.hydraulic.util.UNIT

class CustomSpinnerAdapter(
    context: Context,
    textViewResourceId: Int,
    objects: Array<UNIT>
) : ArrayAdapter<UNIT> (context, textViewResourceId, objects) {
    private val mContext = context
    private val mObject = objects
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =  super.getView(position, convertView, parent)
        val textView: AppCompatTextView = view.findViewById(android.R.id.text1)
        textView.text = mObject[position].unit
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =  super.getDropDownView(position, convertView, parent)
        val textView: CheckedTextView = view.findViewById(android.R.id.text1)
        textView.text = mObject[position].unit
        return view
    }
}