package com.uniquespm.hydraulic.ui.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.model.Cylinder

class ProjectListAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mProjectList: List<Cylinder> = ArrayList()

    companion object {
        private const val TAG = "FormulaListAdapter"
    }

    fun updateProject(projectList: List<Cylinder>) {
        mProjectList = projectList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_list_item, parent, false)
        return ProjectViewHolder(mContext, view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProjectViewHolder).setAdapterData(mProjectList[position])
    }

    override fun getItemCount(): Int  = mProjectList.size

}

class ProjectViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val projectTextView: TextView

    init {
        projectTextView = view.findViewById(R.id.project_name)
    }

    fun setAdapterData(cylinder: Cylinder) {
        projectTextView.text = cylinder.mProjectName
    }
}
