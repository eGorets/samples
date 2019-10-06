package com.sample.movie.providers

import android.graphics.Bitmap
import com.sample.movie.Movie
import io.reactivex.Observable
import okhttp3.ResponseBody

interface IMovieProvider {

    fun fetchMovies(page: Int): Observable<List<Movie>>

    fun loadImage(path: String): Observable<Bitmap>

}
