package com.example.jobsearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobsearch.api.RetrofitInstance
import com.example.jobsearch.db.FavouriteDatabase
import com.example.jobsearch.models.FavouriteJob
import com.example.jobsearch.models.RemoteJobResponse

class RemoteJobRepository(val db : FavouriteDatabase) {

    private val remoteJobApi = RetrofitInstance.apiService
    private val remoteJobResponseLiveData = MutableLiveData<RemoteJobResponse>()

    val jobs: LiveData<RemoteJobResponse>
        get() = remoteJobResponseLiveData

    suspend fun getRemoteJobResponse() {
        val result = remoteJobApi.getRemoteJobResponse()
        if(result.body() != null){
            remoteJobResponseLiveData.postValue(result.body())
        }
    }

    suspend fun addFavouriteJob(job: FavouriteJob) = db.getFavJobDao().addFavouriteJob(job)
    suspend fun deleteJob(job: FavouriteJob) = db.getFavJobDao().deleteFavJob(job)
    fun getAllFavJob() = db.getFavJobDao().getAllFavJob()

}