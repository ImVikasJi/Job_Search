package com.example.jobsearch

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.example.jobsearch.databinding.ActivityMainBinding
import com.example.jobsearch.db.FavouriteDatabase
import com.example.jobsearch.repository.RemoteJobRepository
import com.example.jobsearch.viewmodel.RemoteJobViewModel
import com.example.jobsearch.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val remoteViewModel: RemoteJobViewModel by viewModels {
        RemoteJobViewModelFactory(
            application, RemoteJobRepository(
                FavouriteDatabase(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
    }


}