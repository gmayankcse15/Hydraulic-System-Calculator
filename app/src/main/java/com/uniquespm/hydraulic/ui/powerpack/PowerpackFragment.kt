package com.uniquespm.hydraulic.ui.powerpack

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.uniquespm.hydraulic.R
import kotlinx.android.synthetic.main.fragment_powerpack.*

class PowerpackFragment : Fragment() {

    private lateinit var powerpackViewModel: PowerpackViewModel
    private lateinit var mContext: Context
    private val unitLength = arrayOf("mm", "cm", "inch")
    private val unitPressure = arrayOf("bar", "Psi", "Kgcm²")
    private val unitSpeed = arrayOf("mm/sec", "m/min")
    private val unitFlow = arrayOf("lpm", "m³/min", "gpm")
    private val unitMotor = arrayOf("HP", "Kw")
    private val unitForce = arrayOf("Ton", "Newton")
    private val unitVolume = arrayOf("l")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_powerpack, container, false)
        return root
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
            unitSpeed
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
//            area_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitSpeed
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            speed_spinner.adapter = adapter
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

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitFlow
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            flow_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitMotor
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            motor_spinner.adapter = adapter
        }

        ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item,
            unitVolume
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            oil_stroke_spinner.adapter = adapter
            tank_capacity_spinner.adapter = adapter
        }

    }
}
