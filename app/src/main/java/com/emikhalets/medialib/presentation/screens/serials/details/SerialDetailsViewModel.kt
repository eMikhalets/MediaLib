package com.emikhalets.medialib.presentation.screens.serials.details

import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.use_case.serials.SerialDetailsUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SerialDetailsViewModel @Inject constructor(
    private val useCase: SerialDetailsUseCase,
) : BaseViewModel<SerialDetailsState>() {

    override fun initState() = SerialDetailsState()

    fun resetError() = setState { it.copy(error = null) }

    fun getSerial(serialId: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            if (serialId == 0L) {
                setState { it.copy(loading = false) }
            } else {
                useCase.getSerial(serialId)
                    .onSuccess { entity -> setState { it.copy(loading = false, entity = entity) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            }
        }
    }

    fun updateSerialRating(rating: Int) {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                useCase.updateSerialRating(rating, entity)
                    .onSuccess { setState { it.copy(loading = false) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_save_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    fun updateSerialPoster(poster: String) {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                useCase.updateSerialPosterUrl(poster, entity)
                    .onSuccess { setState { it.copy(loading = false) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_save_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    fun deleteSerial() {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                useCase.deleteSerial(entity)
                    .onSuccess { setState { it.copy(loading = false, deleted = true) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_delete_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }
}
