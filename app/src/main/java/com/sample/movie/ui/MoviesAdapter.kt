package com.sample.movie.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.movie.Movie
import com.sample.movie.PopularResponse
import com.sample.movie.R
import com.sample.movie.databinding.MovieItemViewBinding
import com.sample.movie.di.ServiceLocator
import com.sample.movie.providers.IMovieProvider
import com.sample.movie.providers.INetworkStatusProvider
import com.sample.movie.viewmodels.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}

class MoviesAdapter : PagedListAdapter<Movie, MoviesViewHolder>(DIFF_CALLBACK) {

    val onItemClickObservable = PublishSubject.create<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingView = DataBindingUtil.inflate<MovieItemViewBinding>(
            inflater,
            R.layout.movie_item_view,
            parent,
            false
        )
        return MoviesViewHolder(bindingView)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position) as Movie
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onItemClickObservable.onNext(getItem(position)!!)
        }
    }
}

class MoviesViewHolder(private val itemViewBinding: MovieItemViewBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bind(movie: Movie) {
        itemViewBinding.movie = movie
        itemViewBinding.executePendingBindings()
    }
}


@SuppressLint("CheckResult")
class MoviesPageDataSource(private val viewModel: BaseViewModel): ItemKeyedDataSource<Int, Movie>() {

    var page = 1

    private val moviesProvider: IMovieProvider =
        ServiceLocator.lookUp(IMovieProvider::class)

    private val networkStatusProvider: INetworkStatusProvider =
        ServiceLocator.lookUp(INetworkStatusProvider::class)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Movie>
    ) {
        networkStatusProvider.observeNetworkStatus()
            .filter { it }
            .flatMap { moviesProvider.fetchMovies(1) }
            .doOnSubscribe(viewModel::disposeOnDestroy)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callback.onResult(it)
            }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Movie>) {
        moviesProvider.fetchMovies(params.key)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(viewModel::disposeOnDestroy)
            .subscribe {
                callback.onResult(it)
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Movie>) {
    }

    override fun getKey(item: Movie) = ++page

}

class MovieDataFactory(private val viewModel: BaseViewModel) : DataSource.Factory<Int, Movie>() {

    override fun create(): DataSource<Int, Movie> = MoviesPageDataSource(viewModel)

}
