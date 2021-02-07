package com.uniquespm.hydraulic.ui.website

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uniquespm.hydraulic.R
import kotlinx.android.synthetic.main.fragment_formula.*

class WebsiteFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_formula, container, false)
        return root
    }
}
