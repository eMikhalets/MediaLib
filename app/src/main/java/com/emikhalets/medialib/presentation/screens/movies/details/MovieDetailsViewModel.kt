package com.emikhalets.medialib.presentation.screens.movies.details

import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.domain.use_case.movies.MovieDetailsUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.enums.ItemType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: MovieDetailsUseCase,
) : BaseViewModel<MovieDetailsState>() {

    override fun initState() = MovieDetailsState()

    fun resetError() = setState { it.copy(error = null) }

    fun getMovie(movieId: Long) {
        setState { it.copy(loading = true) }
        if (movieId == 0) {
            setState { it.copy(loading = false) }
        }

        launchDefault {
            _state.update { DetailsState.Loading }
            val response = when (type) {
                ItemType.MOVIE -> databaseRepo.getMovieFlow(id)
                ItemType.SERIAL -> databaseRepo.getSerialFlow(id)
                ItemType.BOOK -> databaseRepo.getBookFlow(id)
                ItemType.MUSIC -> databaseRepo.getMovieFlow(id) // TODO: change to music request
            }
            response
                .onSuccess { flow ->
                    flow.collectLatest { item ->
                        item?.let { _state.update { DetailsState.Item(item) } }
                        this@MovieDetailsViewModel.item = item
                    }
                }
                .onFailure { exception ->
                    _state.update { DetailsState.Error(exception.message) }
                }
        }
    }

    fun updateMovie(rating: Int, type: ItemType) {
        item ?: return

        launchDefault {
            _state.update { DetailsState.Loading }
            val response = when (type) {
                ItemType.MOVIE -> {
                    val copy = (item as MovieDbEntity).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
                ItemType.SERIAL -> {
                    val copy = (item as SerialDbEntity).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
                ItemType.BOOK -> {
                    val copy = (item as BookDB).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
                ItemType.MUSIC -> {
                    // TODO: change to music entity
                    val copy = (item as MovieDbEntity).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
            }
            response.onFailure { exception ->
                _state.update { DetailsState.Error(exception.message) }
            }
        }
    }

    fun updateMovie(poster: String, type: ItemType) {
        item ?: return
        if (poster != item?.poster) return

        launchDefault {
            _state.update { DetailsState.Loading }
            val response = when (type) {
                ItemType.MOVIE -> {
                    val copy = (item as MovieDbEntity).copy(poster = poster)
                    databaseRepo.updateItem(copy)
                }
                ItemType.SERIAL -> {
                    val copy = (item as SerialDbEntity).copy(poster = poster)
                    databaseRepo.updateItem(copy)
                }
                ItemType.BOOK -> {
                    val copy = (item as BookDB).copy(poster = poster)
                    databaseRepo.updateItem(copy)
                }
                ItemType.MUSIC -> {
                    // TODO: change to music entity
                    val copy = (item as MovieDbEntity).copy(poster = poster)
                    databaseRepo.updateItem(copy)
                }
            }
            response.onFailure { exception ->
                _state.update { DetailsState.Error(exception.message) }
            }
        }
    }

    fun deleteMovie(type: ItemType) {
        item ?: return

        launchDefault {
            _state.update { DetailsState.Loading }
            val response = when (type) {
                ItemType.MOVIE -> databaseRepo.deleteItem(item as MovieDbEntity)
                ItemType.SERIAL -> databaseRepo.deleteItem(item as SerialDbEntity)
                ItemType.BOOK -> databaseRepo.deleteItem(item as BookDB)
                ItemType.MUSIC -> databaseRepo.deleteItem(item as MovieDbEntity) // TODO: change to music entity
            }
            response
                .onSuccess {
                    _state.update { DetailsState.ItemDeleted }
                }
                .onFailure { exception ->
                    _state.update { DetailsState.Error(exception.message) }
                }
        }
    }
}
