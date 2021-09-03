package com.example.akmovies.data.models

import java.io.Serializable


data class TvShow(
    val country: String,
    val end_date: Any,
    val id: Int,
    val image_thumbnail_path: String,
    val name: String,
    val network: String,
    val permalink: String,
    val start_date: String,
    val status: String
):Serializable