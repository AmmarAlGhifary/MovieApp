package com.blogspot.yourfavoritekaisar.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.blogspot.yourfavoritekaisar.movieapp.R
import com.blogspot.yourfavoritekaisar.movieapp.model.Movie
import com.blogspot.yourfavoritekaisar.movieapp.rep.MovieRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // "::" di gunakan untuk mengreferences class atau function,
        // Ada alternatifnya tapi gw lagi mager
        MovieRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        Log.d("MainActivity", "Movies: $movies")
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}