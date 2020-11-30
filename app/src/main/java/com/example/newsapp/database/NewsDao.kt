package com.example.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
suspend fun insert(article: Article): Long

//it is going to return live data so it doesn't wwork well with suspend
@Query("SELECT * FROM articles")
fun getAllArticles(): LiveData<List<Article>>

@Delete
suspend fun deleteArticle(article: Article)
}