package com.example.jobsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobsearch.databinding.JobLayoutAdapterBinding
import com.example.jobsearch.models.FavouriteJob
import com.example.jobsearch.models.Job
import com.example.jobsearch.ui.fragments.MainFragmentDirections

class FavJobAdapter : RecyclerView.Adapter<FavJobAdapter.RemoteJobViewHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    inner class RemoteJobViewHolder(itemBinding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<FavouriteJob>() {
        override fun areItemsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        binding =
            JobLayoutAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RemoteJobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {
            binding?.let {
                Glide.with(this)
                    .load(currentJob.companyLogoUrl)
                    .into(it.ivCompanyLogo)
            }

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType

            val dateJob = currentJob.publicationDate.split("T")
            binding?.tvDate?.text = dateJob.get(0)
        }.setOnClickListener { mView ->
            val tags = arrayListOf<String>()
            val job = Job(
                currentJob.candidateRequiredLocation,
                currentJob.category,
                currentJob.companyLogoUrl,
                currentJob.companyName,
                currentJob.description,
                currentJob.id,
                currentJob.jobType,
                currentJob.publicationDate,
                currentJob.salary,
                tags,
                currentJob.title,
                currentJob.url,
            )

            val direction =
                MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job)

            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}