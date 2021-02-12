package com.uniquespm.hydraulic.ui.symbol

import android.content.Context
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R

class SymbolListAdapter(val mContext: Context, val unitList: Array<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "FormulaListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.symbol_list_item, parent, false)
        return FormulaViewHolder(mContext, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FormulaViewHolder).setSymbolResource(unitList[position])
    }

    override fun getItemCount(): Int  = unitList.size

}

class FormulaViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val imageView : ImageView = view.findViewById(R.id.symbol_view)
    fun setSymbolResource(resId: Int) {
        imageView.setImageResource(resId)
    }
}
