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

data class SerialEditScreenState(
    val serial: SerialDB? = null,
    val saved: Boolean = false,
) {

    fun setSerial(serial: SerialDB): SerialEditScreenState {
        return this.copy(serial = serial)
    }

    fun setSaved(): SerialEditScreenState {
        return this.copy(saved = true)
    }
}

@HiltViewModel
class SerialEditViewModel @Inject constructor(
    private val repo: SerialsRepository,
) : ViewModel() {

    var state by mutableStateOf(SerialEditScreenState())
        private set


    fun getSerial(id: Int?) {
        if (id == null || id == 0) return
        viewModelScope.launch {
            repo.getItem(id).onSuccess { flow ->
                flow.collectLatest { serial -> state = state.setSerial(serial) }
            }
        }
    }

    fun saveSerial(serial: SerialDB) {
        viewModelScope.launch {
            if (serial.id == 0) {
                repo.insertItem(serial).onSuccess { state = state.setSaved() }
            } else {
                repo.updateItem(serial).onSuccess { state = state.setSaved() }
            }
        }
    }
}