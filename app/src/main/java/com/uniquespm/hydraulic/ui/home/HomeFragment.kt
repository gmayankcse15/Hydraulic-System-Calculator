package com.uniquespm.hydraulic.ui.home

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.uniquespm.hydraulic.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val constraintSet = ConstraintSet()
        constraintSet.clone(home_constraint_layout)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            constraintSet.connect(
                R.id.cardview_cylinder,
                ConstraintSet.BOTTOM,
                R.id.home_constraint_layout,
                ConstraintSet.BOTTOM
            )
            constraintSet.connect(
                R.id.cardview_cylinder,
                ConstraintSet.END,
                R.id.cardview_powerpack,
                ConstraintSet.START
            )

            constraintSet.connect(
                R.id.cardview_powerpack,
                ConstraintSet.START,
                R.id.cardview_cylinder,
                ConstraintSet.END
            )
            constraintSet.connect(
                R.id.cardview_powerpack,
                ConstraintSet.TOP,
                R.id.home_constraint_layout,
                ConstraintSet.TOP
            )
        } else {
            constraintSet.connect(
                R.id.cardview_cylinder,
                ConstraintSet.BOTTOM,
                R.id.cardview_powerpack,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                R.id.cardview_cylinder,
                ConstraintSet.END,
                R.id.home_constraint_layout,
                ConstraintSet.END
            )

            constraintSet.connect(
                R.id.cardview_powerpack,
                ConstraintSet.START,
                R.id.home_constraint_layout,
                ConstraintSet.START
            )
            constraintSet.connect(
                R.id.cardview_powerpack,
                ConstraintSet.TOP,
                R.id.cardview_cylinder,
                ConstraintSet.BOTTOM
            )
        }
        constraintSet.applyTo(home_constraint_layout)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardview_cylinder.setOnClickListener {
            val navController = Navigation.findNavController(it);
            navController.popBackStack()
            Navigation.findNavController(it).navigate(R.id.cardview_cylinder)
        }

        cardview_powerpack.setOnClickListener{
            val navController = Navigation.findNavController(it);
            navController.popBackStack()
            Navigation.findNavController(it).navigate(R.id.cardview_powerpack)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
