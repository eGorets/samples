package com.sample.movie.providers.impl

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.sample.movie.providers.INetworkStatusProvider
import io.reactivex.Observable

class NetworkStatusProvider(val context: Context) : INetworkStatusProvider {

    override fun observeNetworkStatus(): Observable<Boolean>
        = ReactiveNetwork.observeNetworkConnectivity(context)
        .map { it.available() }

}
