package com.emikhalets.medialib.presentation.screens.edit

import androidx.lifecycle.ViewModel
import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.data.repository.DatabaseRepositoryImpl
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val databaseRepo: DatabaseRepositoryImpl,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state get() = _state.asStateFlow()

    fun getItemData(id: Int?, type: ItemType) {
        id ?: return
        launchDefault {
            val response = when (type) {
                ItemType.MOVIE -> databaseRepo.getMovieFlow(id)
                ItemType.SERIAL -> databaseRepo.getSerialFlow(id)
                ItemType.BOOK -> databaseRepo.getBookFlow(id)
                ItemType.MUSIC -> databaseRepo.getMovieFlow(id) // TODO: change to music request
            }
            response.onSuccess { flow ->
                flow.collectLatest { item -> _state.update { it.setItem(item) } }
            }
        }
    }

    fun saveItem(item: ViewListItem?, type: ItemType) {
        item ?: return
        launchDefault {
            when (type) {
                ItemType.MOVIE -> saveItem(item as MovieDbEntity)
                ItemType.SERIAL -> saveItem(item as SerialDbEntity)
                ItemType.BOOK -> saveItem(item as BookDB)
                ItemType.MUSIC -> saveItem(item as MovieDbEntity) // TODO: change to music entity
            }
        }
    }

    private suspend fun saveItem(item: MovieDbEntity) {
        val response = if (item.id == 0) databaseRepo.insertItem(item)
        else databaseRepo.updateItem(item)

        response.onSuccess { _state.update { it.setSaved() } }
    }

    private suspend fun saveItem(item: SerialDbEntity) {
        val response = if (item.id == 0) databaseRepo.insertItem(item)
        else databaseRepo.updateItem(item)

        response.onSuccess { _state.update { it.setSaved() } }
    }

    private suspend fun saveItem(item: BookDB) {
        val response = if (item.id == 0) databaseRepo.insertItem(item)
        else databaseRepo.updateItem(item)

        response.onSuccess { _state.update { it.setSaved() } }
    }

    data class State(
        val item: ViewListItem? = null,
        val saved: Boolean = false,
    ) {

        fun setItem(item: ViewListItem?): State {
            return this.copy(item = item)
        }

        fun setSaved(): State {
            return this.copy(saved = true)
        }
    }
}