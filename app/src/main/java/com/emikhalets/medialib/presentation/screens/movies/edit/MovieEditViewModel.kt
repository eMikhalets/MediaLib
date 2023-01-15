package com.emikhalets.medialib.presentation.screens.movies.edit

import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.use_case.movies.MovieEditUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieEditViewModel @Inject constructor(
    private val useCase: MovieEditUseCase,
) : BaseViewModel<MovieEditState>() {

    override fun initState() = MovieEditState()

    fun resetError() = setState { it.copy(error = null) }

    fun getMovie(movieId: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            if (movieId == 0L) {
                val entity = MovieFullEntity(
                    movieEntity = MovieEntity(
                        id = 0,
                        title = "",
                        titleRu = "",
                        year = 0,
                        rating = 0,
                        watchStatus = MovieWatchStatus.NONE,
                        overview = "",
                        poster = "",
                        saveTimestamp = 0,
                        lastUpdateTimestamp = 0,
                        comment = "",
                        runtime = "",
                        awards = ""
                    ),
                    genres = emptyList(),
                    ratings = emptyList(),
                    crew = emptyList()
                )
                setState { it.copy(loading = false, entity = entity) }
            } else {
                useCase.getMovie(movieId)
                    .onSuccess { entity -> setState { it.copy(loading = false, entity = entity) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            }
        }
    }

    fun saveMovie(
        title: String,
        titleRu: String,
        genres: String,
        year: Int,
        comment: String,
        watchStatus: MovieWatchStatus,
        rating: Int,
        overview: String,
    ) {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                val movieEntity = entity.movieEntity.copy(
                    title = title,
                    titleRu = titleRu,
                    year = year,
                    comment = comment,
                    watchStatus = watchStatus,
                    rating = rating,
                    overview = overview
                )
                val newEntity = entity.copy(movieEntity = movieEntity, genres = parseGenres(genres))
                useCase.saveMovie(newEntity)
                    .onSuccess { setState { it.copy(loading = false, saved = true) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_save_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }

    private fun parseGenres(genres: String): List<GenreEntity> {
        return try {
            val arr = genres.split(", ")
            arr.map { GenreEntity(it, GenreType.MOVIE) }
        } catch (ex: Exception) {
            emptyList()
        }
    }
}