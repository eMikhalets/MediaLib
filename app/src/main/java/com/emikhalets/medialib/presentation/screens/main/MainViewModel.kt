package com.emikhalets.medialib.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.support.ViewListItem
import com.emikhalets.medialib.data.repository.DatabaseRepository
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseRepo: DatabaseRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state get() = _state.asStateFlow()

    private var searchJob: Job? = null

    fun getMainData() {
        launchDefault {
            databaseRepo.getMovies().onSuccess { movies ->
                movies.collectLatest { list -> _state.update { it.setMovies(list) } }
            }
            databaseRepo.getSerials().onSuccess { serials ->
                serials.collectLatest { list -> _state.update { it.setSerials(list) } }
            }
            databaseRepo.getBooks().onSuccess { books ->
                books.collectLatest { list -> _state.update { it.setBooks(list) } }
            }
        }
    }

    fun searchItems(query: String, type: ItemType) {
        cancelJob(searchJob, "Starting a new search request")
        searchJob = launchDefault {
            delay(750)
            when (type) {
                ItemType.MOVIE -> searchMovies(query)
                ItemType.SERIAL -> searchSerials(query)
                ItemType.BOOK -> searchBooks(query)
                ItemType.MUSIC -> searchMovies(query) // TODO: change to music searching
            }
        }
    }

    private suspend fun searchMovies(query: String) {
        databaseRepo.getMovies(query).onSuccess { movies ->
            movies.collectLatest { list -> _state.update { it.setMovies(list) } }
        }
    }

    private suspend fun searchSerials(query: String) {
        databaseRepo.getSerials(query).onSuccess { serials ->
            serials.collectLatest { list -> _state.update { it.setSerials(list) } }
        }
    }

    private suspend fun searchBooks(query: String) {
        databaseRepo.getBooks(query).onSuccess { books ->
            books.collectLatest { list -> _state.update { it.setBooks(list) } }
        }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }

    data class State(
        val movies: List<MovieDB> = emptyList(),
        val serials: List<SerialDB> = emptyList(),
        val books: List<BookDB> = emptyList(),
    ) {

        fun setMovies(movies: List<MovieDB>): State {
            return this.copy(movies = movies)
        }

        fun setSerials(serials: List<SerialDB>): State {
            return this.copy(serials = serials)
        }

        fun setBooks(books: List<BookDB>): State {
            return this.copy(books = books)
        }
    }
}