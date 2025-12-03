package com.example.test_lab_week_13

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_lab_week_13.databinding.ActivityMainBinding
import com.example.test_lab_week_13.model.Movie
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Setup DataBinding
        // Pastikan layout XML Anda juga menggunakan package 'test_lab_week_13' di bagian <variable>
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 2. Setup Adapter
        val movieAdapter = MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                Toast.makeText(this@MainActivity, "Clicked: ${movie.title}", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, DetailsActivity::class.java)

                // PERBAIKAN: Kirim data satu per satu (String) agar 'putExtra' tidak error
                // Kita ambil konstanta kunci dari DetailsActivity
                intent.putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
                intent.putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
                intent.putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
                intent.putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)

                startActivity(intent)
            }
        })

        // Access RecyclerView via binding object
        binding.movieList.layoutManager = LinearLayoutManager(this)
        binding.movieList.adapter = movieAdapter

        // Setup Repository
        val movieRepository = (application as MovieApplication).movieRepository

        // Setup ViewModel
        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        // 3. Bind ViewModel to XML
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this
    }
}