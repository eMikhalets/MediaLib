package com.emikhalets.medialib.presentation.screens.serials

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.repository.SerialsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SerialsScreenState(
    val serials: List<SerialDB> = emptyList(),
) {

    fun setSerials(serials: List<SerialDB>): SerialsScreenState {
        return this.copy(serials = serials)
    }
}

@HiltViewModel
class SerialsViewModel @Inject constructor(
    private val repo: SerialsRepository,
) : ViewModel() {

    var state by mutableStateOf(SerialsScreenState())
        private set

    private var serialsJob: Job? = null

    fun getSerials(query: String) {
        cancelJob(serialsJob, "Starting a new search request")
        serialsJob = viewModelScope.launch {
            val moviesResponse = if (query.isEmpty()) {
                repo.getItems()
            } else {
                delay(750)
                repo.getItems(query)
            }
            moviesResponse.onSuccess { movies ->
                movies.collectLatest { state = state.setSerials(it) }
            }
        }
    }

    fun addSerial(serial: SerialDB) {
        viewModelScope.launch {
            repo.insertItem(serial)
        }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}