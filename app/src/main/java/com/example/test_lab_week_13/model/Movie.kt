package com.example.test_lab_week_13.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize // Agar bisa dikirim lewat Intent
@Entity(tableName = "movies") // Agar bisa disimpan di Database Room
data class Movie(
    @PrimaryKey // Menjadikan ID sebagai kunci utama database
    val id: Int,

    val title: String?,

    val overview: String?,

    @Json(name = "release_date")
    val releaseDate: String?,

    @Json(name = "poster_path")
    val posterPath: String?,

    // Perbaikan Modul Step 11: Tambahkan default value "" untuk mencegah error database
    @Json(name = "backdrop_path")
    val backdropPath: String? = "",

    val popularity: Double,

    // Field tambahan dari API (Opsional, boleh dihapus jika tidak dipakai di UI)
    @Json(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @Json(name = "vote_count")
    val voteCount: Int? = 0,

    val adult: Boolean? = false
) : Parcelable