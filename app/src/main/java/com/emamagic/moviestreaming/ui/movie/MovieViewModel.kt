package com.emamagic.moviestreaming.ui.movie

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel<MovieState, CommonEffect, MovieEvent>() {

    override fun createInitialState() = MovieState.initialize()

    override fun handleEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.GetDetailMovie -> getDetailMovie(event.id)
            is MovieEvent.GetSeasons -> getSeasons(event.id)
            is MovieEvent.PlayVideoClicked -> playVideoClicked(event.videoLink)
            is MovieEvent.SeasonClicked -> seasonClicked(event.seasonId)
        }.exhaustive
    }

    private fun getDetailMovie(id: Long) = viewModelScope.launch {
        setEffect { CommonEffect.Loading(true) }
        val movie = repository.getMovieById(id)
        setState { copy(movie = movie, currentState = CurrentMovieState.MOVIE_RECEIVED) }
        repository.getCastsById(id).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(casts = it.data!! ,currentState = CurrentMovieState.CASTS_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setState { copy(casts = it.data!! ,currentState = CurrentMovieState.CASTS_RECEIVED) }
                    setEffect { CommonEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(casts = it.data!! ,currentState = CurrentMovieState.CASTS_RECEIVED) }
            }.exhaustive
            setEffect { CommonEffect.Loading(false) }
        }
    }


    private fun getSeasons(id: Long) = viewModelScope.launch{
        repository.getSeasonById(id).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(seasons = it.data!! ,currentState = CurrentMovieState.SEASONS_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setState { copy(seasons = it.data!! ,currentState = CurrentMovieState.SEASONS_RECEIVED) }
                    setEffect { CommonEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(seasons = it.data!! ,currentState = CurrentMovieState.SEASONS_RECEIVED) }
            }.exhaustive
        }
    }


    private fun playVideoClicked(videoLink: String) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(MovieFragmentDirections.actionMovieFragmentToFragmentVideoPlayer(videoLink)) }
    }


    private fun seasonClicked(seasonId: Long) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(MovieFragmentDirections.actionMovieFragmentToEpisodeListFragment(seasonId)) }
    }


}