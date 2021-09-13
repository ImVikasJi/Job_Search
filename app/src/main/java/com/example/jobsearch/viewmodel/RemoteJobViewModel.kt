package com.example.jobsearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.jobsearch.repository.RemoteJobRepository

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app) {
    fun remoteJobResult() = remoteJobRepository.remoteJobResult()
}