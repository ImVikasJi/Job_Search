package com.example.jobsearch.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearch.R
import com.example.jobsearch.adapter.FavJobAdapter
import com.example.jobsearch.databinding.FragmentSavedJobBinding
import com.example.jobsearch.db.FavouriteDatabase
import com.example.jobsearch.models.FavouriteJob
import com.example.jobsearch.repository.RemoteJobRepository
import com.example.jobsearch.viewmodel.RemoteJobViewModel
import com.example.jobsearch.viewmodel.RemoteJobViewModelFactory


class SavedJobFragment : Fragment(R.layout.fragment_saved_job),
    FavJobAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!

    private lateinit var favJobAdapter: FavJobAdapter

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
        _binding = FragmentSavedJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        favJobAdapter = FavJobAdapter(this)

        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(
                activity, LinearLayout.VERTICAL
            ) {})
            adapter = favJobAdapter
        }

        remoteViewModel.getAllFavouriteJobs().observe(viewLifecycleOwner) { favJob ->
            favJobAdapter.differ.submitList(favJob)
            updateUI(favJob)

        }
    }

    private fun updateUI(favJob: List<FavouriteJob>?) {
        if (favJob!!.isNotEmpty()) {
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else {
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(job: FavouriteJob, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: FavouriteJob) {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Are you sure you want to permanently delete this")
            setPositiveButton("DELETE") { _, _ ->
                remoteViewModel.deleteJob(job)
                Toast.makeText(activity, "Job Deleted", Toast.LENGTH_LONG).show()
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }
}