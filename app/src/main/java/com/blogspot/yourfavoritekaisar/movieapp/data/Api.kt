package com.blogspot.yourfavoritekaisar.movieapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String = "fa2c849fca1204cdc46abea78adbbb86",
        @Query("page") page: Int
    ) : Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "fa2c849fca1204cdc46abea78adbbb86",
        @Query("page") page: Int
    ) : Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") api: String = "fa2c849fca1204cdc46abea78adbbb86",
        @Query("page") page: Int
    ) : Call<MovieResponse>
}