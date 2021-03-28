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
import com.uniquespm.hydraulic.util.*

class ConvertorFragment : Fragment() {

    private lateinit var mConvetorList: RecyclerView
    private val list: Array<String> = arrayOf("Length", "Volume", "Mass", "Pressure", "Force", "Temperature", "Time", "Speed", "Area", "Energy", "Flow", "Angle")
    private val unitList: ArrayList<Array<UNIT>> = ArrayList()

    init {
        unitList.add(arrayOf(LENGTH.MM, LENGTH.CM, LENGTH.METER, LENGTH.INCH, LENGTH.FEET))
        unitList.add(
            arrayOf(
                VOLUME.LITRE,
                VOLUME.ML,
                VOLUME.MM3,
                VOLUME.CM3,
                VOLUME.M3,
                VOLUME.USGallon,
                VOLUME.UKGallon
            )
        )
        unitList.add(arrayOf(MASS.KG, MASS.GRAM, MASS.TONS, MASS.MG, MASS.POUND))
        unitList.add(
            arrayOf(
                PRESSURE.BAR,
                PRESSURE.PASCAL,
                PRESSURE.PSI,
                PRESSURE.ATM,
                PRESSURE.KGCM2
            )
        )
        unitList.add(arrayOf(FORCE.TON, FORCE.NEWTON, FORCE.KGF, FORCE.POUND))
        unitList.add(arrayOf(TEMPERATURE.CO, TEMPERATURE.FO, TEMPERATURE.KO))
        unitList.add(arrayOf(TIME.SEC, TIME.MIN, TIME.HOUR))
        unitList.add(arrayOf(SPEED.MM_SEC, SPEED.M_SEC, SPEED.MM_MIN, SPEED.M_MIN))
        unitList.add(arrayOf(AREA.M2, AREA.MM2, AREA.CM2, AREA.FT2, AREA.INCH2))
        unitList.add(arrayOf(ENERGY.HP, ENERGY.WATT, ENERGY.KW, ENERGY.Calorie))
        unitList.add(arrayOf(FLOW.L_MIN, FLOW.M3_MIN, FLOW.FPM))
        unitList.add(arrayOf(ANGLE.RADIAN, ANGLE.DEGREE, ANGLE.MINUTE, ANGLE.SECOND))
    }

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
