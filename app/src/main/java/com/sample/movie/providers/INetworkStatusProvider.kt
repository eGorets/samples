package com.sample.movie.providers

import io.reactivex.Observable

interface INetworkStatusProvider {

    fun observeNetworkStatus(): Observable<Boolean>

}
