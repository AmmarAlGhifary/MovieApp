package com.blogspot.yourfavoritekaisar.movieapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String = "fa2c849fca1204cdc46abea78adbbb86",
        @Query("page") page: Int
    ) : Call<MovieResponse>

}