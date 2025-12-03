package com.example.test_lab_week_13

import android.util.Log
import com.example.test_lab_week_13.api.MovieService
import com.example.test_lab_week_13.database.MovieDao
import com.example.test_lab_week_13.database.MovieDatabase
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase // <--- INI WAJIB ADA AGAR TIDAK ERROR "Unresolved reference"
) {
    private val apiKey = "90895dc4461c40ff8c17c3119ba29a6f"

    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            // Karena movieDatabase ada di constructor, ini tidak akan error lagi
            val movieDao: MovieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getMovies()

            if (savedMovies.isEmpty()) {
                val movies = movieService.getPopularMovies(apiKey).results
                movieDao.addMovies(movies)
                emit(movies)
            } else {
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }

    // Fungsi untuk WorkManager (Commit 3)
    suspend fun fetchMoviesFromNetwork() {
        val movieDao: MovieDao = movieDatabase.movieDao()
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            val moviesFetched = popularMovies.results
            movieDao.addMovies(moviesFetched)
        } catch (exception: Exception) {
            Log.d("MovieRepository", "Error: ${exception.message}")
        }
    }
}