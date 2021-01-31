package com.uniquespm.hydraulic.ui.cylinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uniquespm.hydraulic.R

class CylinderFragment : Fragment() {

    private lateinit var cylinderViewModel: CylinderViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        cylinderViewModel =
//                ViewModelProviders.of(this).get(CylinderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cylinder, container, false)
//        val textView: TextView = root.findViewById(R.id.text_contactus)
//        cylinderViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}
