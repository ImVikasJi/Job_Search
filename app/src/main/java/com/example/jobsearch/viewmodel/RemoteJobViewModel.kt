package com.example.jobsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.jobsearch.models.RemoteJobResponse
import com.example.jobsearch.repository.RemoteJobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            remoteJobRepository.getRemoteJobResponse()
        }
    }

    val jobs: LiveData<RemoteJobResponse>
        get() = remoteJobRepository.jobs
}