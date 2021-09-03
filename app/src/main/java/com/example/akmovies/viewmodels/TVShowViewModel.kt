package com.example.akmovies.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.akmovies.data.api.MovieAPI
import com.example.akmovies.data.models.MovieDetails
import com.example.akmovies.data.paging.MoviePagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TVShowViewModel @Inject constructor(
    private val api: MovieAPI
):ViewModel() {


    val movieDetail:MutableLiveData<MovieDetails> = MutableLiveData()

    val tvShowData = Pager(
        PagingConfig(pageSize = 20)
    ){
        MoviePagingDataSource(api)
    }.flow.cachedIn(viewModelScope)

    fun getShowDetails(movieID:Int) = viewModelScope.launch {
        val response = api.getMovieDetails(movieID)
        val data = response.body()
        movieDetail.postValue(data)
    }
}