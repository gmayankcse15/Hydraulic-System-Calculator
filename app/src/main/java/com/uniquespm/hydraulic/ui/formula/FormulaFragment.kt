package com.uniquespm.hydraulic.ui.formula

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R

class FormulaFragment : Fragment() {

    private lateinit var mContext: Context;
    private lateinit var mFormulaList: RecyclerView
    private val formulaList: Array<Array<String>> = arrayOf(
        arrayOf("Area"),
        arrayOf("Volume"),
        arrayOf("Pressure"),
        arrayOf("Flow Rate"),
        arrayOf("Power"),
        arrayOf("Torque"),
        arrayOf("Force"),
        arrayOf("Velocity"),
        arrayOf("Burst Pressure")
    )
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_formula, container, false)
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFormulaList = view.findViewById(R.id.formula_list)
        val mFormulaAdapter = FormulaListAdapter(mContext, formulaList);
        mFormulaList.layoutManager = LinearLayoutManager(mContext)
        mFormulaList.adapter = mFormulaAdapter
    }

}
