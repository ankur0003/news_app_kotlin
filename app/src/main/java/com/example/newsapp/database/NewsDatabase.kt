package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Article
import com.example.newsapp.api.Converters

@TypeConverters(Converters::class)
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getArticleDao(): NewsDao

    companion object{
        @Volatile
        private var INSTANCE: NewsDatabase?=null

        fun getInstance(context: Context) : NewsDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance== null){
                    instance = Room.databaseBuilder(
                                    context.applicationContext,
                                    NewsDatabase::class.java,
                                    "news_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}