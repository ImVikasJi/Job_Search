package com.example.jobsearch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.jobsearch.databinding.FragmentJobDetailsBinding
import com.example.jobsearch.db.FavouriteDatabase
import com.example.jobsearch.models.FavouriteJob
import com.example.jobsearch.models.Job
import com.example.jobsearch.repository.RemoteJobRepository
import com.example.jobsearch.viewmodel.RemoteJobViewModel
import com.example.jobsearch.viewmodel.RemoteJobViewModelFactory
import com.google.android.material.snackbar.Snackbar


class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJob: Job
    private val args: JobDetailsFragmentArgs by navArgs()

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
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener {
            addFavJob(view)
        }
    }

    private fun addFavJob(view: View) {
        val favJob = FavouriteJob(
            0,
            currentJob.candidateRequiredLocation,
            currentJob.category,
            currentJob.companyLogoUrl,
            currentJob.companyName,
            currentJob.description,
            currentJob.id,
            currentJob.jobType,
            currentJob.publicationDate,
            currentJob.salary,
            currentJob.title,
            currentJob.url,
        )
        remoteViewModel.addFavJob(favJob)
        Snackbar.make(view,"Job Saved Successfully",Snackbar.LENGTH_LONG).show()
    }


    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url.let {
                loadUrl(it)
                Toast.makeText(requireContext(), "WebView Loaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

}