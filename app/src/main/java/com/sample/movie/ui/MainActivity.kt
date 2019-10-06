package com.sample.movie.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.movie.Movie
import com.sample.movie.R
import com.sample.movie.databinding.ActivityMainBinding
import com.sample.movie.viewmodels.PopularViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(PopularViewModel::class.java)

        val adapter = MoviesAdapter()

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                lifecycleOwner = this@MainActivity
                this.adapter = adapter
                this.model = viewModel
            }

        viewModel.liveData.observe(this, Observer {
            adapter.submitList(it)
        })

        adapter.onItemClickObservable
            .subscribe {
                DetailsDialog()
                    .apply {
                        arguments = bundleOf("movie" to it)
                    }
                    .show(supportFragmentManager, "")

            }

    }
}
