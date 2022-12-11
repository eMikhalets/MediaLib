package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.presentation.core.AppBookStatusSpinner
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.AppTextFieldRead
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.YearDialog
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus

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
        bookId = bookId,
        book = viewModel.state.book,
        onSaveClick = { viewModel.saveBook(it) }
    )
}

@Composable
private fun BookEditScreen(
    navController: NavHostController,
    bookId: Int?,
    book: BookDB?,
    onSaveClick: (BookDB) -> Unit,
) {
    if ((bookId ?: 0) > 0 && book == null) return
    AppScaffold(navController, book?.title) {
        val localFocusManager = LocalFocusManager.current
        var showYearDialog by remember { mutableStateOf(false) }

        var title by remember { mutableStateOf(book?.title ?: "") }
        var titleRu by remember { mutableStateOf(book?.titleRu ?: "") }
        var author by remember { mutableStateOf(book?.author ?: "") }
        var genres by remember { mutableStateOf(book?.genres ?: "") }
        var releaseYear by remember { mutableStateOf(book?.releaseYear ?: 0) }
        var comment by remember { mutableStateOf(book?.comment ?: "") }
        var status by remember { mutableStateOf(book?.status?.toString() ?: "") }
        var rating by remember { mutableStateOf(book?.rating ?: 0) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { localFocusManager.clearFocus() })
                }
        ) {
            Spacer(modifier = Modifier.height(8.dp))
//            AppTextField(title, { title = it }, stringResource(R.string.app_title))
            Spacer(modifier = Modifier.height(8.dp))

//            AppTextField(titleRu, { titleRu = it }, stringResource(R.string.app_title_ru))
            Spacer(modifier = Modifier.height(8.dp))

//            AppTextField(author, { author = it }, stringResource(R.string.book_author))
            Spacer(modifier = Modifier.height(8.dp))

//            AppTextField(genres, { genres = it }, stringResource(R.string.app_genres))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextFieldRead(releaseYear.toString(), label = stringResource(R.string.app_year),
                onClick = { showYearDialog = true })
            Spacer(modifier = Modifier.height(8.dp))

//            AppTextField(comment, { comment = it }, stringResource(R.string.app_comment))
            Spacer(modifier = Modifier.height(16.dp))

            RatingBar(rating = rating, onRatingChange = { rating = it })
            Spacer(modifier = Modifier.height(16.dp))

            AppBookStatusSpinner(
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
                            releaseYear = releaseYear,
                            comment = comment,
                            status = ItemStatus.get(status),
                            rating = rating
                        )
                    )
                }
            ) {
                Text(text = stringResource(id = R.string.app_save))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (showYearDialog) {
            YearDialog(
                year = releaseYear,
                onDismiss = { showYearDialog = false },
                onOkClick = {
                    releaseYear = it
                    showYearDialog = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        BookEditScreen(
            navController = rememberNavController(),
            bookId = 1,
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