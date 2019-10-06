package com.sample.movie.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableField
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sample.movie.Movie
import com.sample.movie.di.ServiceLocator
import com.sample.movie.providers.IMovieProvider
import com.sample.movie.providers.INetworkStatusProvider
import com.sample.movie.ui.MovieDataFactory
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor

@SuppressLint("CheckResult")
class PopularViewModel : BaseViewModel() {

    // inject
    private val networkStatusProvider: INetworkStatusProvider =
        ServiceLocator.lookUp(INetworkStatusProvider::class)

    val networkObservable = ObservableField<Boolean>(false)

    private val dataFactory = MovieDataFactory(this)
    private val config = PagedList.Config.Builder()
        .setPrefetchDistance(10)
        .setEnablePlaceholders(true)
        .build()

    val liveData = LivePagedListBuilder<Int, Movie>(dataFactory, config).build()

    init {
        networkStatusProvider
            .observeNetworkStatus()
            .doOnSubscribe(::disposeOnDestroy)
            .doOnNext { networkObservable.set(it) }
            .subscribe()
    }

}
