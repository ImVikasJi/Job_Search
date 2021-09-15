package com.example.jobsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.jobsearch.models.RemoteJobResponse
import com.example.jobsearch.repository.RemoteJobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

}