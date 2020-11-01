package com.blogspot.yourfavoritekaisar.movieapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.yourfavoritekaisar.movieapp.R
import com.blogspot.yourfavoritekaisar.movieapp.adapter.MovieAdapter
import com.blogspot.yourfavoritekaisar.movieapp.data.Movie
import com.blogspot.yourfavoritekaisar.movieapp.data.rep.MovieRepository
import com.blogspot.yourfavoritekaisar.movieapp.data.rep.MovieRepository.getUpcomingMovies

class MainActivity : AppCompatActivity() {

    //    data sementara untuk popular movies
    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MovieAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private var popularMoviesPage = 1

    //    Data sementara untuk top-rated
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MovieAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private var topRatedMoviesPage = 1

    //    Data sementara untuk up-coming
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MovieAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager
    private var upcomingMoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Gatau kenapa masang self-assignment nanti di apus
        getPopularMovies()
        popularMovies = findViewById(R.id.rv_popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MovieAdapter(mutableListOf())
        popularMoviesAdapter = popularMoviesAdapter

        getTopRatedMovies()
        topRatedMovies = findViewById(R.id.rv_top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedMoviesAdapter = MovieAdapter(mutableListOf())
        topRatedMoviesAdapter = topRatedMoviesAdapter

        getUpcomingMovies()
        upcomingMovies = findViewById(R.id.rv_upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MovieAdapter(mutableListOf())
        upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun getTopRatedMovies() {
        MovieRepository.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    //         "::" di gunakan untuk mengreferences class atau function,
    //         Ada alternatifnya tapi gw lagi mager
    private fun getPopularMovies() {
        MovieRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun getUpcomingMovies() {
        getUpcomingMovies(
            upcomingMoviesPage,
            ::onUpcomingMoviesFetched,
            ::onError
        )
    }

    //    Buat mengambil data popular
    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
        Log.d("MainActivity", "Popular: $movies")
    }

    //     Buat mengambil data rated
    private fun onTopRatedMoviesFetched(movies: List<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
        Log.d("MainActivity", "Top Rated: $movies")

    }

    //     Buat mengambil data upcoming
    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
        Log.d("MainActivity", "Up coming: $movies")
    }


    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    //     totalItemCount = Total movie di dalam Adapter, Ini bakal nambah tiap kali kita manggil [popularMoviesAdapter.appendMovies()]
    //     visibileItemCount = Ini jumlah child yang di pasang di recycleview dimana data yang di Recycle
    //     firstVisibleItem = Posisi item nya nanti di bagian kiri
    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibileItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

//          Kondisi ini akan benar jika pengguna telah mengscroll setengah jalan terus ditambah nilai buffered dari nilai visibleItemCount yang terlihat.
                if (firstVisibleItem + visibileItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    //     totalItemCount = Total movie di dalam Adapter, Ini bakal nambah tiap kali kita manggil [popularMoviesAdapter.appendMovies()]
    //     visibileItemCount = Ini jumlah child yang di pasang di recycleview dimana data yang di Recycle
    //     firstVisibleItem = Posisi item nya nanti di bagian kiri
    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

//                      Kondisi ini akan benar jika pengguna telah mengscroll setengah jalan terus ditambah nilai buffered dari nilai visibleItemCount yang terlihat.
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    //     totalItemCount = Total movie di dalam Adapter, Ini bakal nambah tiap kali kita manggil [popularMoviesAdapter.appendMovies()]
    //     visibileItemCount = Ini jumlah child yang di pasang di recycleview dimana data yang di Recycle
    //     firstVisibleItem = Posisi item nya nanti di bagian kiri
    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

//                    Kondisi ini akan benar jika pengguna telah mengscroll setengah jalan terus ditambah nilai buffered dari nilai visibleItemCount yang terlihat.
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }

}


//        MovieRepository.getPopularMovies(
//            onSuccess = ::onPopularMoviesFetched,
//            onError = ::onError
//        )