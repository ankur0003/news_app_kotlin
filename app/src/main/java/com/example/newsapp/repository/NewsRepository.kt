package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.database.NewsDatabase
import com.example.newsapp.models.Article

class NewsRepository(val db: NewsDatabase) {

    /**the purpose of the REPOSITORY
     * is to get the data from the Databse or From the RETROFIT API
     * So this REPOSITORY will hjave the function that queries our databse
     * directly
     */

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchForNews(q:String,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(q,pageNumber)

    suspend fun insert(article: Article)  = db.getArticleDao().insert(article)
    suspend fun delete(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()
}