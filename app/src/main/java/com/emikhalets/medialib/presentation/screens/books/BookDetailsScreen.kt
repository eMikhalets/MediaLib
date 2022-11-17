package com.emikhalets.medialib.presentation.screens.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.DeleteDialog
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.RootSaveDelete
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.px

@Composable
fun BookDetailsScreen(
    navController: NavHostController,
    bookId: Int?,
    viewModel: BookDetailsViewModel = hiltViewModel(),
) {
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var isNeedSave by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getBook(bookId)
    }

    LaunchedEffect(viewModel.state.book) {
        val book = viewModel.state.book
        comment = book?.comment ?: ""
        rating = book?.rating ?: 0
    }

    LaunchedEffect(viewModel.state.deleted) {
        if (viewModel.state.deleted) {
            navController.popBackStack()
        }
    }

    BookDetailsScreen(
        navController = navController,
        book = viewModel.state.book,
        comment = comment,
        rating = rating,
        isNeedSave = isNeedSave,
        onCommentChange = {
            comment = it
            isNeedSave = true
        },
        onRatingChange = {
            rating = it
            isNeedSave = true
        },
        onUpdateClick = {
            viewModel.updateBook(comment, rating)
            isNeedSave = false
        },
        onDeleteClick = { showDeleteDialog = true },
    )

    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = { showDeleteDialog = false },
            onDeleteClick = {
                viewModel.deleteBook()
                showDeleteDialog = false
            }
        )
    }
}

@Composable
private fun BookDetailsScreen(
    navController: NavHostController,
    book: BookDB?,
    comment: String,
    rating: Int,
    isNeedSave: Boolean,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    AppScaffold(navController, book?.title) {
        if (book == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.app_loading_error),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            BookItem(
                book = book,
                comment = comment,
                rating = rating,
                onCommentChange = onCommentChange,
                onRatingChange = onRatingChange,
            )
        }

        RootSaveDelete(
            isNeedSave = isNeedSave,
            onDeleteClick = onDeleteClick,
            onSaveClick = onUpdateClick
        )
    }
}

@Composable
private fun BookItem(
    book: BookDB,
    comment: String,
    rating: Int,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.poster)
                    .crossfade(true)
                    .transformations(RoundedCornersTransformation(8.px))
                    .error(R.drawable.ph_poster)
                    .build(),
                contentDescription = "",
                placeholder = painterResource(R.drawable.ph_poster),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(150.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = book.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                val localeTitle = book.getLocaleTitle()
                if (localeTitle.isNotEmpty()) {
                    Text(
                        text = localeTitle,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.author,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.releaseYear.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                RatingBar(
                    rating = rating,
                    onRatingChange = onRatingChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        OutlinedTextField(
            value = comment,
            onValueChange = onCommentChange,
            label = { Text(stringResource(R.string.app_comment)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        if (book.overview.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.app_overview),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = book.overview,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.app_rating_value,
                book.voteAverage.toString()),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.app_genres_value, book.genres),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        BookDetailsScreen(
            navController = rememberNavController(),
            book = BookDB(
                title = "Spider-man",
                titleRu = "Spider-man rus",
                genres = "Drama",
                releaseYear = 2018,
                author = "Sample author",
                overview = "overview overview overview overview overview overview overview overview overview overview"
            ),
            comment = "Test comment",
            rating = 4,
            isNeedSave = false,
            onCommentChange = {},
            onRatingChange = {},
            onUpdateClick = {},
            onDeleteClick = {},
        )
    }
}