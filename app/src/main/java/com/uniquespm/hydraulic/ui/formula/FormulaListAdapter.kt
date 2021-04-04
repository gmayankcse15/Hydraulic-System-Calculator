package com.uniquespm.hydraulic.ui.formula

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R

class FormulaListAdapter(val mContext: Context, val unitList: Array<Array<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "FormulaListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.formula_list_item,
            parent,
            false
        )
        return FormulaViewHolder(mContext, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FormulaViewHolder).setAdapterData(unitList[position])
    }

    override fun getItemCount(): Int  = unitList.size


}

class FormulaViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val property = view.findViewById<TextView>(R.id.formula_parameter)
    val formula = view.findViewById<io.github.kexanie.library.MathView>(R.id.formula);
    val parameters = view.findViewById<TextView>(R.id.formula_variables)

    fun setAdapterData(list: Array<String>) {
        property.text = list[0]
        formula.text = list[1]
        parameters.text = list[2]
    }
}
