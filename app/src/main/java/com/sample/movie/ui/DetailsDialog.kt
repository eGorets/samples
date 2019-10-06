package com.sample.movie.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sample.movie.Movie
import com.sample.movie.databinding.DetailLayoutBinding
import com.sample.movie.di.ServiceLocator
import com.sample.movie.providers.IMovieProvider
import com.sample.movie.viewmodels.DetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers


@SuppressLint("CheckResult")
@BindingAdapter("app:url")
fun ImageView.loadImage(url: String) {
    val moviesProvider: IMovieProvider =
        ServiceLocator.lookUp(IMovieProvider::class)

    moviesProvider.loadImage(url)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            this.setImageBitmap(it)
        }
}

class DetailsDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DetailLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DetailLayoutBinding.inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@DetailsDialog
            binding = this
        }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = arguments!!.getSerializable("movie") as Movie

        val model = ViewModelProvider(this@DetailsDialog, DetailModelFactory(movie)).get(DetailsViewModel::class.java)
        binding.model = model
    }

}


class DetailModelFactory(val movie: Movie): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailsViewModel(movie) as T

}