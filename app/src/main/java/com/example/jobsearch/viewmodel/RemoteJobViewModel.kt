package com.example.jobsearch.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.jobsearch.models.FavouriteJob
import com.example.jobsearch.models.Job
import com.example.jobsearch.models.RemoteJobResponse
import com.example.jobsearch.repository.RemoteJobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query

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

    fun getAllFavouriteJobs() = remoteJobRepository.getAllFavJob()

    fun searchJob(searchQuery: String?) = viewModelScope.launch(Dispatchers.IO) {
        if (searchQuery != null) {
            remoteJobRepository.searchJob(searchQuery)
        }else{
            Toast.makeText(getApplication(), "Nothing to show", Toast.LENGTH_SHORT).show()
        }
    }

    fun searchJobResult() = remoteJobRepository.searchJobResult()

}