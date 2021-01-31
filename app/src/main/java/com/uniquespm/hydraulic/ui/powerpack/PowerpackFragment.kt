package com.uniquespm.hydraulic.ui.powerpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uniquespm.hydraulic.R

class PowerpackFragment : Fragment() {

    private lateinit var powerpackViewModel: PowerpackViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        powerpackViewModel =
//                ViewModelProviders.of(this).get(PowerpackViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_powerpack, container, false)
//        val textView: TextView = root.findViewById(R.id.text_contactus)
//        powerpackViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}
