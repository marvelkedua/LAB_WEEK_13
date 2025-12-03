package com.example.test_lab_week_13.model // Sesuaikan package

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Class ini membungkus list movie dari API
@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    @Json(name = "page") val page: Int?,
    @Json(name = "results") val results: List<Movie>, // List Movie ada di sini
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?
)