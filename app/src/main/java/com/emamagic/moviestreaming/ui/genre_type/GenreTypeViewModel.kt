package com.emamagic.moviestreaming.ui.genre_type

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.repository.genre_type.GenreTypeRepository
import com.emamagic.moviestreaming.ui.genre_type.contract.CurrentGenreTypeState
import com.emamagic.moviestreaming.ui.genre_type.contract.GenreTypeEvent
import com.emamagic.moviestreaming.ui.genre_type.contract.GenreTypeState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreTypeViewModel @Inject constructor(
   private val typeRepository: GenreTypeRepository
): BaseViewModel<GenreTypeState , CommonEffect,GenreTypeEvent>() {

    override fun createInitialState() = GenreTypeState.initialize()

    override fun handleEvent(event: GenreTypeEvent) {
        when(event){
            GenreTypeEvent.GetAllGenreType -> getAllGenre()
            is GenreTypeEvent.GenreTypeClicked -> genreTypeClicked(event.genreName)
        }.exhaustive
    }

    private fun getAllGenre() = viewModelScope.launch {
        setEffect { CommonEffect.Loading(true) }
        typeRepository.getAllGenre().collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(genreList = it.data!! ,currentState = CurrentGenreTypeState.RECEIVE_GENRES) }
                is ResultWrapper.Failed -> {
                    setState { copy(genreList = it.data!! ,currentState = CurrentGenreTypeState.RECEIVE_GENRES) }
                    setEffect { CommonEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(genreList = it.data!! ,currentState = CurrentGenreTypeState.RECEIVE_GENRES) }
            }.exhaustive
            setEffect { CommonEffect.Loading(false) }
        }
    }

    private fun genreTypeClicked(genreName: String) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(GenreTypeFragmentDirections.actionGenreTypeFragmentToGenreListFragment(genreName)) }
    }


}