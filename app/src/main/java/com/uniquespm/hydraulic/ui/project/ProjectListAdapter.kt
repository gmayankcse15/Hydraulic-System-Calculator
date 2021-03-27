package com.uniquespm.hydraulic.ui.project

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.model.Cylinder
import com.uniquespm.hydraulic.model.HydraulicSystem
import com.uniquespm.hydraulic.model.Powerpack
import com.uniquespm.hydraulic.util.Constants.Companion.CYLINDER
import com.uniquespm.hydraulic.util.Constants.Companion.POWERPACK

class ProjectListAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mProjectList: List<HydraulicSystem> = ArrayList()

    companion object {
        private const val TAG = "FormulaListAdapter"
    }

    fun updateProject(projectList: List<HydraulicSystem>) {
        mProjectList = projectList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_list_item, parent, false)
        return ProjectViewHolder(mContext, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProjectViewHolder).setAdapterData(mProjectList[position])
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position: $position  Powerpack: ${mProjectList[position]}")

            if (mProjectList[position].mProjectType == CYLINDER) {
                val action = ProjectFragmentDirections.actionNavProjectToCardviewCylinder().setCylinderData(mProjectList[position] as Cylinder)
                it.findNavController().navigate(action)
            } else if (mProjectList[position].mProjectType == POWERPACK){
                val action = ProjectFragmentDirections.actionNavProjectToCardviewPowerpack().setPowerpackData(mProjectList[position] as Powerpack)
                it.findNavController().navigate(action)
            }

        }
    }

    override fun getItemCount(): Int  = mProjectList.size

}

class ProjectViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val projectTextView: TextView

    init {
        projectTextView = view.findViewById(R.id.project_name)
    }

    fun setAdapterData(system: HydraulicSystem) {
        projectTextView.text = system.mProjectName
    }
}
