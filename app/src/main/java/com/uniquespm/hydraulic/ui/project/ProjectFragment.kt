package com.uniquespm.hydraulic.ui.project

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniquespm.hydraulic.R
import com.uniquespm.hydraulic.model.Cylinder

class ProjectFragment : Fragment() {

    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var mProjectListAdapter: ProjectListAdapter
    private lateinit var mContext: Context
    private lateinit var mProjectList: RecyclerView
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        mProjectListAdapter = ProjectListAdapter(mContext)
        projectViewModel.getAllProjects().observe(this.viewLifecycleOwner, Observer {
            it?.let { mProjectListAdapter.updateProject(it) }
        })
        val root = inflater.inflate(R.layout.fragment_project, container, false)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProjectList = view.findViewById(R.id.project_list)
        mProjectList.layoutManager = LinearLayoutManager(mContext)
        mProjectList.adapter = mProjectListAdapter

    }
}
