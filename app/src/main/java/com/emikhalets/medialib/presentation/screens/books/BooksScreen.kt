package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToBookDetails
import com.emikhalets.medialib.presentation.navToBookEdit
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun BooksScreen(
    navController: NavHostController,
    viewModel: BooksViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getBooks(query)
    }

    BooksScreen(
        navController = navController,
        query = query,
        books = viewModel.state.books,
        onAddClick = {
            navController.navToBookEdit(null)
        },
        onQueryChange = {
            query = it
            viewModel.getBooks(query)
        },
        onBookClick = {
            navController.navToBookDetails(it)
        },
    )
}

@Composable
private fun BooksScreen(
    navController: NavHostController,
    query: String,
    books: List<BookDB>,
    onAddClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onBookClick: (Int) -> Unit,
) {
    AppScaffold(navController) {
        RootScreenList(
            query = query,
            list = books,
            searchPlaceholder = stringResource(R.string.books_query_placeholder),
            onAddClick = onAddClick,
            onQueryChange = onQueryChange,
            onItemClick = onBookClick
        ) { item ->
            BookItem(item as BookDB, onBookClick)
        }
    }
}

@Composable
private fun BookItem(
    book: BookDB,
    onBookClick: (Int) -> Unit,
) {
    RootListItem(
        item = book,
        onItemClick = onBookClick
    ) {
        Text(
            text = stringResource(R.string.book_author_value, book.author),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.app_genres_value, book.genres),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        BooksScreen(
            navController = rememberNavController(),
            query = "",
            books = listOf(),
            onAddClick = {},
            onQueryChange = {},
            onBookClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    AppTheme {
        BookItem(
            book = BookDB(
                title = "Long Spider-man",
                genres = "Drama",
                releaseYear = 1859,
                author = "Sample Author",
            ),
            onBookClick = {},
        )
    }
}