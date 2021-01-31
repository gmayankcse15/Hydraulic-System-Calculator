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
        val html = "<a href=\"http://www.uniquespm.com\">www.uniquespm.com</a>"
        val result = Html.fromHtml(html)
        text_website.text = result
        text_website.movementMethod = LinkMovementMethod.getInstance()
        return root
    }
}
