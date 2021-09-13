package com.example.jobsearch.api

import com.example.jobsearch.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface JobApi {

    @GET("remote-jobs")
    suspend fun getRemoteJobResponse(): Response<RemoteJobResponse>
}