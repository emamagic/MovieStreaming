package com.emamagic.moviestreaming.repository.movie_list

import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    fun getAllMovie(category: String): Flow<ResultWrapper<List<MovieEntity>>>
}