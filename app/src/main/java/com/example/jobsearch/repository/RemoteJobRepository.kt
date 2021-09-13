package com.example.jobsearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobsearch.api.RetrofitInstance
import com.example.jobsearch.models.RemoteJobResponse
import retrofit2.Retrofit

class RemoteJobRepository {

    private val remoteJobApi = RetrofitInstance.apiService
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJobResponse> = MutableLiveData()

    suspend fun getRemoteJobResponse() = RetrofitInstance.apiService.getRemoteJobResponse()

    fun remoteJobResult(): LiveData<RemoteJobResponse>{
        return remoteJobResponseLiveData
    }
}