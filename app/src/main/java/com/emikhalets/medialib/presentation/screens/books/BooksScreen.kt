package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.presentation.core.AppDialog
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToBookDetails
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun BooksScreen(
    navController: NavHostController,
    viewModel: BooksViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getBooks(query)
    }

    BooksScreen(
        navController = navController,
        query = query,
        books = viewModel.state.books,
        showAddDialog = showAddDialog,
        onAddClick = {
            showAddDialog = false
            viewModel.addBook(it)
        },
        onAddDialogVisible = { showAddDialog = it },
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
    showAddDialog: Boolean,
    onAddClick: (BookDB) -> Unit,
    onAddDialogVisible: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onBookClick: (Int) -> Unit,
) {
    AppScaffold(navController) {
        RootScreenList(
            query = query,
            list = books,
            searchPlaceholder = stringResource(R.string.books_query_placeholder),
            onAddClick = { onAddDialogVisible(true) },
            onQueryChange = onQueryChange,
            onItemClick = onBookClick
        ) { item ->
            BookItem(item as BookDB, onBookClick)
        }

        if (showAddDialog) {
            AddBookDialog(
                onDismiss = { onAddDialogVisible(false) },
                onAddClick = onAddClick
            )
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

@Composable
private fun AddBookDialog(
    onDismiss: () -> Unit,
    onAddClick: (BookDB) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var titleRu by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    AppDialog(
        label = stringResource(id = R.string.book_add_title),
        onDismiss = { onDismiss() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.add_new_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text(stringResource(id = R.string.book_author)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = titleRu,
                onValueChange = { titleRu = it },
                label = { Text(stringResource(id = R.string.add_new_title_ru)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.add_new_year)) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = genres,
                onValueChange = { genres = it },
                label = { Text(stringResource(R.string.app_genres)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(stringResource(R.string.app_comment)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            IconButton(
                onClick = {
                    onAddClick(
                        BookDB(
                            title = title,
                            titleRu = titleRu,
                            releaseYear = year.toInt(),
                            comment = comment,
                            genres = genres,
                            author = author
                        )
                    )
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
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
            showAddDialog = false,
            onAddClick = {},
            onAddDialogVisible = {},
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

@Preview(showBackground = true)
@Composable
private fun AddBookDialogPreview() {
    AppTheme {
        AddBookDialog(
            onDismiss = {},
            onAddClick = {}
        )
    }
}