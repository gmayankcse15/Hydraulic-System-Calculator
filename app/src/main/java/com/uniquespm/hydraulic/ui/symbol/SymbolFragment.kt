package com.uniquespm.hydraulic.ui.symbol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.uniquespm.hydraulic.R

class SymbolFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_formula, container, false)
//        val textView: TextView = root.findViewById(R.id.text_contactus)
        return root
    }
}
