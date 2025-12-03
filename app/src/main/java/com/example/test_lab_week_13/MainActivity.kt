package com.example.test_lab_week_13

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope // Tambahkan ini
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test_lab_week_13.databinding.ActivityMainBinding
import com.example.test_lab_week_13.model.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch // Tambahkan ini

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Setup Adapter
        val movieAdapter = MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
                intent.putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
                intent.putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
                intent.putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
                startActivity(intent)
            }
        })

        // PENTING: Layout Manager wajib ada
        binding.movieList.layoutManager = LinearLayoutManager(this)
        binding.movieList.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this

        // --- TAMBAHAN KODE DEBUGGING ---

        // 1. Cek apakah ada Error dari ViewModel?
        lifecycleScope.launch {
            movieViewModel.error.collect { errorMsg ->
                if (errorMsg.isNotEmpty()) {
                    // Tampilkan Error di Layar
                    Toast.makeText(this@MainActivity, "ERROR: $errorMsg", Toast.LENGTH_LONG).show()
                    Log.e("DEBUG_APP", "Error dari ViewModel: $errorMsg")
                }
            }
        }

        // 2. Cek apakah Data sebenarnya masuk?
        lifecycleScope.launch {
            movieViewModel.popularMovies.collect { movies ->
                if (movies.isNotEmpty()) {
                    Log.d("DEBUG_APP", "Data masuk: ${movies.size} film")
                } else {
                    Log.w("DEBUG_APP", "Data masih KOSONG (0 film)")
                }
            }
        }
    }
}