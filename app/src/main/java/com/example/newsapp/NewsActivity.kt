package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val articleDatabase = NewsDatabase.getInstance(this)
        val newsRepository= NewsRepository(articleDatabase)
        val viewModelFactory = NewsViewModelFactory(newsRepository)

        newsViewModel = ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)

        val navController = findNavController(R.id.navHostFragment)

        bottomNavView.setupWithNavController(navController)

    }
}
