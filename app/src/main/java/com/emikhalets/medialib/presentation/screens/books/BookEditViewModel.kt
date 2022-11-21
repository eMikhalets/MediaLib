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

data class BookEditScreenState(
    val book: BookDB? = null,
    val saved: Boolean = false,
) {

    fun setBook(book: BookDB): BookEditScreenState {
        return this.copy(book = book)
    }

    fun setSaved(): BookEditScreenState {
        return this.copy(saved = true)
    }
}

@HiltViewModel
class BookEditViewModel @Inject constructor(
    private val repo: BooksRepository,
) : ViewModel() {

    var state by mutableStateOf(BookEditScreenState())
        private set


    fun getBook(id: Int?) {
        id ?: return
        viewModelScope.launch {
            repo.getItem(id).onSuccess { flow ->
                flow.collectLatest { book -> state = state.setBook(book) }
            }
        }
    }

    fun saveBook(book: BookDB) {
        viewModelScope.launch {
            if (book.id == 0) {
                repo.insertItem(book).onSuccess { state = state.setSaved() }
            } else {
                repo.updateItem(book).onSuccess { state = state.setSaved() }
            }
        }
    }
}