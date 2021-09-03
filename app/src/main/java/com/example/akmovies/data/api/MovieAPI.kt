package com.example.akmovies.data.api

import com.example.akmovies.data.models.Movie
import com.example.akmovies.data.models.MovieDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {




    @GET("/api/most-popular")
    suspend fun getPopularMovies(
        @Query("page") pageNumber:Int
    ):Response<Movie>

    @GET("/api/show-details")
    suspend fun getMovieDetails(
        @Query("q") query:Int
    ):Response<MovieDetails>

}