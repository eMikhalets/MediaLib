package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BooksScreenState(
    val books: List<BookDB> = emptyList(),
) {

    fun setBooks(books: List<BookDB>): BooksScreenState {
        return this.copy(books = books)
    }
}

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repo: BooksRepository,
) : ViewModel() {

    var state by mutableStateOf(BooksScreenState())
        private set

    private var booksJob: Job? = null

    fun getBooks(query: String) {
        cancelJob(booksJob, "Starting a new search request")
        booksJob = viewModelScope.launch {
            val moviesResponse = if (query.isEmpty()) {
                repo.getItems()
            } else {
                delay(750)
                repo.getItems(query)
            }
            moviesResponse.onSuccess { movies ->
                movies.collectLatest { state = state.setBooks(it) }
            }
        }
    }

    fun addBook(bookDB: BookDB) {
        viewModelScope.launch {
            repo.insertItem(bookDB)
        }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}