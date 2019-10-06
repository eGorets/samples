package com.sample.movie.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val disposer = CompositeDisposable()

    fun disposeOnDestroy(disposable: Disposable) {
        disposer.add(disposable)
    }

    override fun onCleared() {
        disposer.clear()
        super.onCleared()
    }
}
