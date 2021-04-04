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
        arrayOf("Area\n(Circle)", "$$\\scriptsize \\frac{\\pi \\times D^2}{4}$$", "D -> Diameter"),
        arrayOf("Volume\n(Cylinder)", "\$\$\\scriptsize \\frac{\\pi \\times D^2 \\times L}{4}\$\$", "D -> Diameter\nL -> Length"),
        arrayOf("Pressure", "\$\$\\scriptsize P = \\frac{F}{A}\$\$", "P -> Pressure\nF -> Force\nA -> Area"),
        arrayOf("Flow Rate", "\$\$\\scriptsize A \\times V \\times e\$\$", "Q -> Flow\nA -> Area\nV -> Velocity\ne -> Mechanical +\nVolumetric\nEfficiency"),
        arrayOf("Power", "\$\$\\scriptsize E = Q \\times P\$\$", "E -> Power\nQ -> Flow rate\nP -> Pressure"),
        arrayOf("Torque", "\$\$\\scriptsize T = \\frac{E}{R}\$\$", "T -> Torque\nE -> Power\nR -> Rounds Per Minute"),
        arrayOf("Force", "\$\$\\scriptsize F = P \\times A\$\$", "F -> Force\nP -> Pressure\nA -> Area"),
        arrayOf("Velocity", "\$\$\\scriptsize V = \\frac{Q}{A}\$\$", "V -> Velocity\nQ -> Flow Rate\nA -> Area"),
        arrayOf("Burst\nPressure", "\$\$\\scriptsize Bp = \\frac{2 \\times T.S. \\times t}{o.d}\$\$", "Bp -> Burst Pressure\nof Pipe\nT.S. -> Tensile Strength\nt -> Thickness \nof Pipe\no.d -> Outer Dia")
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
