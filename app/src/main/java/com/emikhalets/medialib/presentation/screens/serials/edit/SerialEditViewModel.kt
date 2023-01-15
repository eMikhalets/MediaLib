package com.emikhalets.medialib.presentation.screens.serials.edit

import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import com.emikhalets.medialib.domain.use_case.serials.SerialEditUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SerialEditViewModel @Inject constructor(
    private val useCase: SerialEditUseCase,
) : BaseViewModel<SerialEditState>() {

    override fun initState() = SerialEditState()

    fun resetError() = setState { it.copy(error = null) }

    fun getSerial(serialId: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            if (serialId == 0L) {
                val entity = SerialFullEntity(
                    serialEntity = SerialEntity(
                        id = 0,
                        title = "",
                        titleRu = "",
                        year = 0,
                        rating = 0,
                        watchStatus = SerialWatchStatus.NONE,
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
                useCase.getSerial(serialId)
                    .onSuccess { entity -> setState { it.copy(loading = false, entity = entity) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            }
        }
    }

    fun saveSerial(
        title: String,
        titleRu: String,
        genres: String,
        year: Int,
        comment: String,
        watchStatus: SerialWatchStatus,
        rating: Int,
        overview: String,
    ) {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                val serialEntity = entity.serialEntity.copy(
                    title = title,
                    titleRu = titleRu,
                    year = year,
                    comment = comment,
                    watchStatus = watchStatus,
                    rating = rating,
                    overview = overview
                )
                val newEntity =
                    entity.copy(serialEntity = serialEntity, genres = parseGenres(genres))
                useCase.saveSerial(newEntity)
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
            arr.map { GenreEntity(it, GenreType.SERIAL) }
        } catch (ex: Exception) {
            emptyList()
        }
    }
}