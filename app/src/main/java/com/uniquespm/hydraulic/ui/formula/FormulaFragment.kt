package com.uniquespm.hydraulic.ui.formula

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uniquespm.hydraulic.R

class FormulaFragment : Fragment() {

    private lateinit var formulaViewModel: FormulaViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        formulaViewModel =
                ViewModelProviders.of(this).get(FormulaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_formula, container, false)
//        val textView: TextView = root.findViewById(R.id.text_contactus)
//        formulaViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}
