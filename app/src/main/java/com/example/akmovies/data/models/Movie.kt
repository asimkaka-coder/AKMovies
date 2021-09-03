package com.example.akmovies.data.models

data class Movie(
    val page: Int,
    val pages: Int,
    val total: String,
    val tv_shows: List<TvShow>
)