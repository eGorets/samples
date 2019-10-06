package com.sample.movie.providers.impl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.sample.movie.network.NetworkClient
import com.sample.movie.providers.IMovieProvider
import io.reactivex.Observable

class MovieProvider: IMovieProvider {

    private val api = NetworkClient.api

    override fun fetchMovies(page: Int)
        = api.loadPopularMovie(page)
        .map { it.movie }


    override fun loadImage(path: String): Observable<Bitmap>
         = api.loadPoster("https://image.tmdb.org/t/p/w500/$path")
            .map { BitmapFactory.decodeStream(it.byteStream()) }
}
