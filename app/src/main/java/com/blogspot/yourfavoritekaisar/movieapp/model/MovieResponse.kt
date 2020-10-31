package com.blogspot.yourfavoritekaisar.movieapp.model

import com.blogspot.yourfavoritekaisar.movieapp.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<Movie>,

    @SerializedName("total_pages")
    val pages: Int
)