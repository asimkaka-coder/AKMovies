package com.example.akmovies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.akmovies.data.api.MovieAPI
import com.example.akmovies.data.models.Movie
import com.example.akmovies.data.models.TvShow
import javax.inject.Inject

class MoviePagingDataSource @Inject constructor(
    private val api: MovieAPI
) :PagingSource<Int, TvShow>(){
    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {

        try {
            val startPageKey = params.key?:1
            val response = api.getPopularMovies(startPageKey)
            val pagedResponse = response.body()
            val dataResult = pagedResponse?.tv_shows

            val prevKey = if (startPageKey == 1) null else startPageKey - 1
            var nextPageNumber: Int? = null
            if(nextPageNumber==pagedResponse?.pages){
                nextPageNumber = null
            }else{
                nextPageNumber = pagedResponse?.page?.plus(1)
            }

            return LoadResult.Page(
                data = dataResult.orEmpty(),
                prevKey = prevKey,
                nextKey = nextPageNumber
            )
        } catch (e:Exception){
            return LoadResult.Error(e)
        }

    }


}