package com.example.jobsearch.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearch.R
import com.example.jobsearch.adapter.RemoteJobAdapter
import com.example.jobsearch.databinding.FragmentSavedJobBinding
import com.example.jobsearch.databinding.FragmentSearchJobBinding
import com.example.jobsearch.db.FavouriteDatabase
import com.example.jobsearch.repository.RemoteJobRepository
import com.example.jobsearch.utils.Constants.TAGSearchJob
import com.example.jobsearch.viewmodel.RemoteJobViewModel
import com.example.jobsearch.viewmodel.RemoteJobViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchJobFragment : Fragment(R.layout.fragment_search_job) {

    private var _binding: FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobAdapter: RemoteJobAdapter

    val remoteViewModel: RemoteJobViewModel by viewModels {
        RemoteJobViewModelFactory(
            requireActivity().application, RemoteJobRepository(
                FavouriteDatabase.invoke(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchJob()
        setUpRecyclerView()
    }

    private fun searchJob() {
        Log.d(TAGSearchJob, "searchJob: Inside")
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {

                        remoteViewModel.searchJob(editable.toString())
                    }
                }
            }
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        jobAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.HORIZONTAL) {})
            adapter = jobAdapter
        }

        remoteViewModel.searchJobResult().observe(viewLifecycleOwner, { remoteJob ->
            jobAdapter.differ.submitList(remoteJob.jobs)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}