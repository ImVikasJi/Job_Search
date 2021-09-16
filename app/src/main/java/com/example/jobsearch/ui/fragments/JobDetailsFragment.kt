package com.example.jobsearch.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.jobsearch.R
import com.example.jobsearch.databinding.FragmentJobDetailsBinding
import com.example.jobsearch.models.Job


class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJob: Job
    private val args: JobDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentJob = args.job!!

        setUpWebView()
    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let {
                loadUrl(it)
                Toast.makeText(requireContext(), "WebView Loaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

}