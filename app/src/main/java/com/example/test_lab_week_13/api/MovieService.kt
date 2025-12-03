package com.example.test_lab_week_13.api

import com.example.test_lab_week_13.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    // suspend function digunakan agar tidak memblokir main thread [cite: 39, 42]
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse // Pastikan model PopularMoviesResponse sudah ada di starter code
}