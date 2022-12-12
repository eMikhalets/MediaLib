package com.emikhalets.medialib.presentation.screens.details

import androidx.lifecycle.ViewModel
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.support.ViewListItem
import com.emikhalets.medialib.data.repository.DatabaseRepository
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state get() = _state.asStateFlow()

    private var item: ViewListItem? = null

    fun getItem(id: Int?, type: ItemType) {
        id ?: return
        launchDefault {
            val response = when (type) {
                ItemType.MOVIE -> databaseRepo.getMovieFlow(id)
                ItemType.SERIAL -> databaseRepo.getSerialFlow(id)
                ItemType.BOOK -> databaseRepo.getBookFlow(id)
                ItemType.MUSIC -> databaseRepo.getMovieFlow(id) // TODO: change to music request
            }
            response.onSuccess { flow ->
                flow.collectLatest { item ->
                    item?.let { _state.update { it.setItem(item) } }
                    this@DetailsViewModel.item = item
                }
            }
        }
    }

    fun updateItem(rating: Int, type: ItemType) {
        item ?: return
        launchDefault {
            when (type) {
                ItemType.MOVIE -> {
                    val copy = (item as MovieDB).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
                ItemType.SERIAL -> {
                    val copy = (item as SerialDB).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
                ItemType.BOOK -> {
                    val copy = (item as BookDB).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
                ItemType.MUSIC -> {
                    // TODO: change to music entity
                    val copy = (item as MovieDB).copy(rating = rating)
                    databaseRepo.updateItem(copy)
                }
            }
        }
    }

    fun updateItem(poster: String, type: ItemType) {
        item ?: return
        if (poster != item?.poster) {
            launchDefault {
                when (type) {
                    ItemType.MOVIE -> {
                        val copy = (item as MovieDB).copy(poster = poster)
                        databaseRepo.updateItem(copy)
                    }
                    ItemType.SERIAL -> {
                        val copy = (item as SerialDB).copy(poster = poster)
                        databaseRepo.updateItem(copy)
                    }
                    ItemType.BOOK -> {
                        val copy = (item as BookDB).copy(poster = poster)
                        databaseRepo.updateItem(copy)
                    }
                    ItemType.MUSIC -> {
                        // TODO: change to music entity
                        val copy = (item as MovieDB).copy(poster = poster)
                        databaseRepo.updateItem(copy)
                    }
                }
            }
        }
    }

    fun deleteItem(type: ItemType) {
        item ?: return
        launchDefault {
            val response = when (type) {
                ItemType.MOVIE -> databaseRepo.deleteItem(item as MovieDB)
                ItemType.SERIAL -> databaseRepo.deleteItem(item as SerialDB)
                ItemType.BOOK -> databaseRepo.deleteItem(item as BookDB)
                ItemType.MUSIC -> databaseRepo.deleteItem(item as MovieDB) // TODO: change to music entity
            }
            response.onSuccess { _state.update { it.setDeleted() } }
        }
    }

    data class State(
        val item: ViewListItem? = null,
        val deleted: Boolean = false,
    ) {

        fun setItem(item: ViewListItem?): State {
            return this.copy(item = item)
        }

        fun setDeleted(): State {
            return this.copy(deleted = true)
        }
    }
}