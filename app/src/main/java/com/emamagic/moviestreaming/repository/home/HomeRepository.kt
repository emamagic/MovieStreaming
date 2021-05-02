package com.emamagic.moviestreaming.repository.home

import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>>

    fun getMoviesByCategory(category: String): Flow<ResultWrapper<List<MovieEntity>>>

    fun getGenre(): Flow<ResultWrapper<List<GenreEntity>>>

    fun enableRefreshMode()

    fun disableRefreshModel()

}