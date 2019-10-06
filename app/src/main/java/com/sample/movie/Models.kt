package com.sample.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val id: Int,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("release_date") val release: String,
    val title: String,
    val overview: String,
    val adult: Boolean
): Serializable

class PopularResponse(
    val page: Int,
    @SerializedName("total_pages") val pageTotal: Int,
    @SerializedName("results") val movie: List<Movie>
)
