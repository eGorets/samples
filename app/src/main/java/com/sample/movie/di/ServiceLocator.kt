@file:Suppress("UNCHECKED_CAST")

package com.sample.movie.di

import android.annotation.SuppressLint
import android.content.Context
import com.sample.movie.providers.IMovieProvider
import com.sample.movie.providers.INetworkStatusProvider
import com.sample.movie.providers.impl.MovieProvider
import com.sample.movie.providers.impl.NetworkStatusProvider
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier

@SuppressLint("StaticFieldLeak")
object ServiceLocator {

    lateinit var context: Context

    fun <T>lookUp(service: KClassifier): T = when (service) {
        IMovieProvider::class -> MovieProvider() as T
        INetworkStatusProvider::class -> NetworkStatusProvider(context) as T
        else -> throw IllegalAccessException("No implementation found")
    }

}
