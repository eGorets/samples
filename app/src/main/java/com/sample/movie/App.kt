package com.sample.movie

import android.app.Application
import android.util.Log
import com.sample.movie.di.ServiceLocator
import io.reactivex.plugins.RxJavaPlugins

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ServiceLocator.context = this

        RxJavaPlugins.setErrorHandler {
            Log.e(App::class.java.simpleName, it.message)
        }

    }

}
