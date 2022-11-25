package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetailsScreenState(
    val book: BookDB? = null,
    val deleted: Boolean = false,
) {

    fun setBook(book: BookDB): BookDetailsScreenState {
        return this.copy(book = book)
    }

    fun setDeleted(): BookDetailsScreenState {
        return this.copy(deleted = true)
    }
}

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val repo: BooksRepository,
) : ViewModel() {

    var state by mutableStateOf(BookDetailsScreenState())
        private set

    private var bookDb: BookDB? = null

    fun getBook(id: Int?) {
        id ?: return
        viewModelScope.launch {
            val moviesResponse = repo.getItem(id)
            moviesResponse.onSuccess { flow ->
                flow.collectLatest { book ->
                    book?.let { state = state.setBook(book) }
                    bookDb = book
                }
            }
        }
    }

    fun updateBook(rating: Int) {
        val book = bookDb?.copy(rating = rating) ?: return
        viewModelScope.launch {
            repo.updateItem(book)
        }
    }

    fun updateBook(poster: String) {
        if (poster != bookDb?.poster) {
            val book = bookDb?.copy(poster = poster) ?: return
            viewModelScope.launch {
                repo.updateItem(book)
            }
        }
    }

    fun deleteBook() {
        val book = bookDb ?: return
        viewModelScope.launch {
            repo.deleteItem(book).onSuccess { state = state.setDeleted() }
        }
    }
}