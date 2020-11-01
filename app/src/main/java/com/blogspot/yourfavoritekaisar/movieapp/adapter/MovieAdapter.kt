package com.blogspot.yourfavoritekaisar.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.yourfavoritekaisar.movieapp.R
import com.blogspot.yourfavoritekaisar.movieapp.data.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

//Kita make Mutable list agar pemanggilan data lebih dinamis
class MovieAdapter(private var movies: MutableList<Movie>, private var onMovieClick: (movie: Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    //Kita make notifyItemRangeInserted(), Agar kita tidak perlu meng-refresh tiap ada data baruh yang masuk
    //Dengan itu kita buatnya agar dia cuman mengnotify tanpa perlu mengrefresh di awal dan di akhir posisi
    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        fun bind(movie: Movie) {
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}