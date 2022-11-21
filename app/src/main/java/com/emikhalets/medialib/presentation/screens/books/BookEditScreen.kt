package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppStatusSpinner
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.BookStatus

@Composable
fun BookEditScreen(
    navController: NavHostController,
    bookId: Int?,
    viewModel: BookEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getBook(bookId)
    }

    LaunchedEffect(viewModel.state.saved) {
        if (viewModel.state.saved) {
            navController.popBackStack()
        }
    }

    BookEditScreen(
        navController = navController,
        book = viewModel.state.book,
        onSaveClick = { viewModel.saveBook(it) }
    )
}

@Composable
private fun BookEditScreen(
    navController: NavHostController,
    book: BookDB?,
    onSaveClick: (BookDB) -> Unit,
) {
    AppScaffold(navController, book?.title) {
        var title by remember { mutableStateOf(book?.title ?: book?.title ?: "") }
        var titleRu by remember { mutableStateOf(book?.titleRu ?: "") }
        var author by remember { mutableStateOf(book?.author ?: "") }
        var genres by remember { mutableStateOf(book?.genres ?: "") }
        var overview by remember { mutableStateOf(book?.overview ?: "") }
        var releaseYear by remember { mutableStateOf(book?.releaseYear?.toString() ?: "") }
        var comment by remember { mutableStateOf(book?.comment ?: "") }
        var status by remember { mutableStateOf(book?.status?.toString() ?: "") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AppTextField(title, { title = it }, stringResource(R.string.app_title))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(titleRu, { titleRu = it }, stringResource(R.string.app_title_ru))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(author, { author = it }, stringResource(R.string.book_author))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(genres, { genres = it }, stringResource(R.string.app_genres))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(overview, { overview = it }, stringResource(R.string.app_overview))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(releaseYear, { releaseYear = it }, stringResource(R.string.app_year),
                KeyboardType.Number)
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(comment, { comment = it }, stringResource(R.string.app_comment))
            Spacer(modifier = Modifier.height(8.dp))

            AppStatusSpinner(
                initItem = book?.status?.toString(),
                onSelect = { status = it.toString() }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSaveClick(
                        BookDB(
                            id = book?.id ?: 0,
                            title = title,
                            titleRu = titleRu,
                            author = author,
                            genres = genres,
                            overview = overview,
                            releaseYear = releaseYear.toInt(),
                            comment = comment,
                            status = BookStatus.valueOf(status)
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.app_save))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        BookEditScreen(
            navController = rememberNavController(),
            book = BookDB(
                id = 1,
                title = "Spider-man",
                genres = "Action, Drama",
                releaseYear = 2018,
                rating = 4,
                overview = "overview overview overview overview overview overview overview overview overview overview",
                comment = "overview overview overview overview overview overview overview overview overview overview"
            ),
            onSaveClick = {}
        )
    }
}