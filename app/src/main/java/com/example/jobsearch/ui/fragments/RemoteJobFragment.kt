package com.example.jobsearch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsearch.R
import com.example.jobsearch.adapter.RemoteJobAdapter
import com.example.jobsearch.databinding.FragmentRemoteJobBinding
import com.example.jobsearch.repository.RemoteJobRepository
import com.example.jobsearch.viewmodel.RemoteJobViewModel
import com.example.jobsearch.viewmodel.RemoteJobViewModelFactory


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job) {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    val viewModel: RemoteJobViewModel by viewModels{
        RemoteJobViewModelFactory(requireActivity(). application, RemoteJobRepository())
    }

    private lateinit var remoteJobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity,LinearLayout.HORIZONTAL){})

            adapter = remoteJobAdapter
        }
        fetchingData()
    }

    private fun fetchingData() {
        viewModel.remoteJobResult().observe(viewLifecycleOwner){ remoteJob ->
            if(remoteJob != null){
                remoteJobAdapter.differ.submitList(remoteJob.jobs)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}