package com.uniquespm.hydraulic.ui.convertor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R

class ConvertorFragment : Fragment() {

    private lateinit var mConvetorList: RecyclerView
    private val list: Array<String> = arrayOf("Length", "Volume", "Mass", "Pressure", "Force", "Temperature", "Time", "Speed", "Area", "Energy", "Flow", "Angle")
    private val unitList: Array<Array<String>> = arrayOf(
        arrayOf("mm", "cm", "meter", "inch", "feet"),
        arrayOf("litre", "ml", "mm\u00b3", "cm\u00b3", "m\u00b3", "us gallon", "uk gallon"),
        arrayOf("kg", "gram", "tons", "mg", "pound"),
        arrayOf("bar", "pascal", "psi", "atm", "kg/cm\u00b2"),
        arrayOf("ton", "newton", "kgf", "pound"),
        arrayOf("C\u00b0", "F\u00b0", "K\u00b0"),
        arrayOf("sec", "min", "sec", "hour"),
        arrayOf("mm/sec", "m/sec", "mm/min", "m/min"),
        arrayOf("m\u00b2", "mm\u00b2", "cm\u00b2", "ft\u00b2", "inch\u00b2"),
        arrayOf("H.P.", "watt", "kw", "w", "calorie"),
        arrayOf("l/min", "m\u00b3/min", "gpm", "l/min"),
        arrayOf("radian", "degree", "minute", "second"))
    private lateinit var mContext: Context

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_convertor, container, false)
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mConvetorList = view.findViewById(R.id.convertor_list)
        mConvetorList.layoutManager = LinearLayoutManager(this.context)
        val convertorAdapter = ConvertorListAdapter(mContext, list, unitList)
        mConvetorList.adapter = convertorAdapter
    }

}
