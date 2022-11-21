package com.emikhalets.medialib.presentation.screens.serials

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.repository.SerialsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SerialDetailsScreenState(
    val serial: SerialDB? = null,
    val deleted: Boolean = false,
) {

    fun setSerial(serial: SerialDB): SerialDetailsScreenState {
        return this.copy(serial = serial)
    }

    fun setDeleted(): SerialDetailsScreenState {
        return this.copy(deleted = true)
    }
}

@HiltViewModel
class SerialDetailsViewModel @Inject constructor(
    private val repo: SerialsRepository,
) : ViewModel() {

    var state by mutableStateOf(SerialDetailsScreenState())
        private set

    private var serialDb: SerialDB? = null

    fun getSerial(id: Int?) {
        id ?: return
        viewModelScope.launch {
            val moviesResponse = repo.getItem(id)
            moviesResponse.onSuccess { flow ->
                flow.collectLatest { serial ->
                    state = state.setSerial(serial)
                    serialDb = serial
                }
            }
        }
    }

    fun updateSerial(rating: Int) {
        val serial = serialDb?.copy(rating = rating) ?: return
        viewModelScope.launch {
            repo.updateItem(serial)
        }
    }

    fun updateSerial(poster: String) {
        if (poster != serialDb?.poster) {
            val serial = serialDb?.copy(poster = poster) ?: return
            viewModelScope.launch {
                repo.updateItem(serial)
            }
        }
    }

    fun deleteSerial() {
        val serial = serialDb ?: return
        viewModelScope.launch {
            repo.deleteItem(serial).onSuccess { state = state.setDeleted() }
        }
    }
}