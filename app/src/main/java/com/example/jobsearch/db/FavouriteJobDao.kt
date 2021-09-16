package com.example.jobsearch.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jobsearch.models.FavouriteJob

@Dao
interface FavouriteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteJob(job: FavouriteJob)

    @Query("Select * from fav_job order by id Desc")
    fun getAllFavJob():LiveData<List<FavouriteJob>>

    @Delete
    suspend fun deleteFavJob(job: FavouriteJob)

}