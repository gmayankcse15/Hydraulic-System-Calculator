package com.uniquespm.hydraulic.ui.cylinder

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uniquespm.hydraulic.R
import kotlinx.android.synthetic.main.fragment_cylinder.*

class CylinderFragment : Fragment() {

    private lateinit var cylinderViewModel: CylinderViewModel
    private lateinit var mContext: Context
    private val unitLength = arrayOf("mm", "cm", "inch")
    private val unitPressure = arrayOf("bar", "Psi", "Kgcm²")
    private val unitArea = arrayOf("mm²", "m²", "ft²")
    private val unitVolume = arrayOf("Ltr")
    private val unitForce = arrayOf("Ton", "Newton")


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
        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitLength
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            bore_spinner.adapter = adapter
            rod_spinner.adapter = adapter
            stroke_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitPressure
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            pressure_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitArea
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            area_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitVolume
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            volume_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitForce
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            force_spinner.adapter = adapter
        }


    }

}
