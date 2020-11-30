package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {

    /**
     * here the [RESOURCE] is aUTIL wraper for network Succes and ERROR or LAAODING state
     */
    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage:Int=1
    var breakingNewsResponse: NewsResponse?=null
    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPage=1
    var searchNewsResponse : NewsResponse?=null

    init{
        getBreakingNews("in")
    }
    fun getBreakingNews(countyCode:String)=

        //viewModelScope says our coroutine will woork as loong as our ViewModel is alive
        viewModelScope.launch {
            /**
             * before making the actual network call
             * we sure want to emit the loading state before making
             * any network requests.
             * So just use[breakingNews] variable and use postValue()
             *
             */
            breakingNews.postValue(Resource.Loading())

            /** And snow we can our Network Requests
             *  and we need to be sure current network response is saved in
             *  the [response] object
             */
            val response = newsRepository
                .getBreakingNews(countyCode,breakingNewsPage)

            //so in this case we can handle our response for that i create a seperate function

            /**
             * now we can call [breakingNews] variable and post a new value
             * with handeBreakingNewsResponse(response) and pass it in the response
             */

            breakingNews.postValue(handleBreakingNewsResponse(response))
        }


    fun getSearchNews(searchQuery: String) {
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())
            val response = newsRepository.searchForNews(searchQuery,searchPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        }
    }
    /**
     * sdo in the below function we can handle
     * if we want to emit the [Successs] or [ERROR]
     */
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{resultResponse->
                breakingNewsPage++
                if(breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                }else{
                    val oldArticles =breakingNewsResponse?. articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let{resultResponse ->
                searchPage++
                if(searchNewsResponse ==null){
                    searchNewsResponse = resultResponse
                }else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)

    }

    fun getSavedNews() = newsRepository.getSavedNews()
}