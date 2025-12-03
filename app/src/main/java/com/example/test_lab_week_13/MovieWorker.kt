package com.example.test_lab_week_13

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        // 1. Ambil repository dari Application class
        val movieRepository = (applicationContext as MovieApplication).movieRepository

        // 2. Jalankan update data di background thread (IO)
        CoroutineScope(Dispatchers.IO).launch {
            movieRepository.fetchMoviesFromNetwork()
        }

        // 3. Beritahu sistem bahwa tugas berhasil dijadwalkan
        return Result.success()
    }
}