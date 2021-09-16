package com.example.jobsearch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobsearch.models.FavouriteJob

@Database(
    entities = [FavouriteJob::class],
    version = 1
)
abstract class FavouriteDatabase : RoomDatabase(){

    abstract fun getFavJobDao(): FavouriteJobDao

    companion object {
        @Volatile
        private var instance: FavouriteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavouriteDatabase::class.java,
                "fav_job_db.db"
            ).build()
    }
}