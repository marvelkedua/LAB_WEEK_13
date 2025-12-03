package com.example.test_lab_week_13

import android.app.Application
import com.example.test_lab_week_13.api.MovieService
import com.example.test_lab_week_13.database.MovieDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory // PENTING!
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // 1. Konfigurasi Moshi agar support Kotlin Data Class
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // <--- INI SOLUSINYA
            .build()

        // 2. Setup Retrofit dengan Moshi yang sudah dikonfigurasi
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Masukkan moshi di sini
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        // 3. Setup Database
        val movieDatabase = MovieDatabase.getInstance(applicationContext)

        // 4. Setup Repository
        movieRepository = MovieRepository(movieService, movieDatabase)
    }
}