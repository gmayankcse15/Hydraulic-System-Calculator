package com.uniquespm.hydraulic.ui.convertor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uniquespm.hydraulic.R

class ConvertorFragment : Fragment() {

    private lateinit var convertorViewModel: ConvertorViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        convertorViewModel =
                ViewModelProviders.of(this).get(ConvertorViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_formula, container, false)
//        val textView: TextView = root.findViewById(R.id.text_contactus)
//        convertorViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}
