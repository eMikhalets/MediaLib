package com.emikhalets.medialib.presentation.screens.serials.list

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.use_case.serials.SerialsListUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SerialsViewModel @Inject constructor(
    private val useCase: SerialsListUseCase,
) : BaseViewModel<SerialsState>() {

    private var searchJob: Job? = null

    override fun initState() = SerialsState()

    fun resetError() = setState { it.copy(error = null) }

    fun getSerialsList() {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.getSerialsList()
                .onSuccess { handleSerialsListSuccess(it) }
                .onFailure { handleFailure(it) }
        }
    }

    fun searchSerials(query: String) {
        cancelJob(searchJob, "Starting a new search request")
        searchJob = launchIO {
            delay(750)
            setState { it.copy(loading = true) }
            useCase.getSerialsListWithQuery(query, currentState.serials)
                .onSuccess { handleSearchSerials(it) }
                .onFailure { handleFailure(it) }
        }
    }

    private suspend fun handleSerialsListSuccess(flow: Flow<List<SerialFullEntity>>) {
        flow.collectLatest { list ->
            setState { it.copy(loading = false, serials = list, showedSerials = list) }
        }
    }

    private suspend fun handleSearchSerials(flow: Flow<List<SerialFullEntity>>) {
        flow.collectLatest { list ->
            setState { it.copy(loading = false, showedSerials = list) }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}