package com.uniquespm.hydraulic.ui.project

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.util.Log
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
import com.uniquespm.hydraulic.ui.cylinder.CylinderFragment
import com.uniquespm.hydraulic.util.Constants.Companion.LOAD_CYLINDER

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
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Position: $position  Cylinder: ${mProjectList[position]}")
            val intent = Intent(LOAD_CYLINDER)
            intent.setClass(mContext, CylinderFragment::class.java)
            intent.putExtra("CYLINDER", mProjectList[position])
            mContext.startActivity(intent)
        }
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
