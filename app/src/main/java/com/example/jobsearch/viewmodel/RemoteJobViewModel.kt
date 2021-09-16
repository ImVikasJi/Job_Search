package com.example.jobsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.jobsearch.models.FavouriteJob
import com.example.jobsearch.models.RemoteJobResponse
import com.example.jobsearch.repository.RemoteJobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okio.IOException

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app) {

    val jobs: LiveData<RemoteJobResponse>
        get() = remoteJobRepository.jobs

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                remoteJobRepository.getRemoteJobResponse()
            } catch (t: Throwable) {

            }
        }
    }

    fun addFavJob(job: FavouriteJob) = viewModelScope.launch(Dispatchers.IO) {
        remoteJobRepository.addFavouriteJob(job)
    }

    fun deleteJob(job: FavouriteJob) = viewModelScope.launch(Dispatchers.IO) {
        remoteJobRepository.deleteJob(job)
    }

    fun getAllFavouriteJobs() = remoteJobRepository.getAllFavJobs()

}