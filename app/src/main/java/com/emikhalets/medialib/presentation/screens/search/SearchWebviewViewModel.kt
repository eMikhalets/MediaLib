package com.emikhalets.medialib.presentation.screens.search

import androidx.lifecycle.ViewModel
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.network.MovieResponse
import com.emikhalets.medialib.data.repository.DatabaseRepository
import com.emikhalets.medialib.data.repository.NetworkRepository
import com.emikhalets.medialib.utils.enums.SearchType
import com.emikhalets.medialib.utils.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchWebviewViewModel @Inject constructor(
    private val networkRepo: NetworkRepository,
    private val databaseRepo: DatabaseRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun parseUrl(url: String, type: SearchType) {
        launchDefault {
            val id: String? = try {
                url.split("/")[4]
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
            _state.update { it.setIdParsed(id) }
        }
    }

    fun searchItem(type: SearchType) {
        launchDefault {
            val id = state.value.idParsed ?: ""
            searchOmdb(id, type)
        }
    }

    private suspend fun searchOmdb(id: String, type: SearchType) {
        networkRepo.searchOmdb(id)
            .onSuccess {
                when (type) {
                    SearchType.MOVIE -> saveMovie(it)
                    SearchType.SERIAL -> saveSerial(it)
                    SearchType.BOOK -> Unit
                    SearchType.MUSIC -> Unit
                }
            }
            .onFailure { ex ->
                _state.update { it.setError(ex.message ?: "") }
            }
    }

    private suspend fun saveMovie(response: MovieResponse) {
        val itemDb = MovieDB.fromResponse(response)
        databaseRepo.insertItem(itemDb)
            .onSuccess { _state.update { it.setSaved() } }
            .onFailure { ex -> _state.update { it.setError(ex.message ?: "") } }
    }

    private suspend fun saveSerial(response: MovieResponse) {
        val itemDb = SerialDB.fromResponse(response)
        databaseRepo.insertItem(itemDb)
            .onSuccess { _state.update { it.setSaved() } }
            .onFailure { ex -> _state.update { it.setError(ex.message ?: "") } }
    }

    data class State(
        val idParsed: String? = null,
        val saved: Boolean = false,
        val error: String = "",
        val errorCounter: Int = 0,
    ) {

        fun setIdParsed(id: String?): State {
            return this.copy(idParsed = id)
        }

        fun setSaved(): State {
            return this.copy(saved = true)
        }

        fun setError(error: String): State {
            return this.copy(error = error, errorCounter = errorCounter + 1)
        }
    }
}