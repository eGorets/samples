package com.sample.movie.network

import com.sample.movie.PopularResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface API {

    @GET("movie/popular?api_key=d2962eb981a1989a6cd7f4f89c426ed3&language=en-US")
    fun loadPopularMovie(@Query("page") page: Int): Observable<PopularResponse>

    @GET
    fun loadPoster(@Url imageUrl: String): Observable<ResponseBody>
}
