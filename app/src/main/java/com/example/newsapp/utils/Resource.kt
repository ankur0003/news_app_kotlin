package com.example.newsapp.utils

/**
 * it will be a sealed class
 *is a kind of an abstract class
 * but we can define which class are allowed
 * to inherit from the resource class
 */
sealed class Resource<T>(val data:T?=null,
                  val message:String?=null
) {

    /**
     * that is a generic class and it is recommended by google to bw used
     * to wrap around our network responses and that will be a generic class
     * and it is very useful to [Differentiate] between [Success] & [Error]
     * responses and also helps to handle loading state.
     * So we can show a progress bar while are processing
     *
     */

    /**
     * in this class we will define three classes and only those are allowed to inhehrit from resources.
     * And that is everyhthhing for the resource class
     * we can use this in every projhect to wrap around the responses and be able to handle
     * your succes stage and error state and loading state
     */

    class Success<T>(data:T):  Resource<T>(data)
    class Error<T>(message: String, data:T?=null): Resource<T>(data,message )
    class Loading<T> : Resource<T>()


}