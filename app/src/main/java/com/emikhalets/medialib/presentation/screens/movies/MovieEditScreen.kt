package com.emikhalets.medialib.presentation.screens.movies

import android.util.Log
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
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppStatusSpinner
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.AppTextFieldRead
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.YearDialog
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus

@Composable
fun MovieEditsScreen(
    navController: NavHostController,
    movieId: Int?,
    viewModel: MovieEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getMovie(movieId)
    }

    LaunchedEffect(viewModel.state.saved) {
        if (viewModel.state.saved) {
            navController.popBackStack()
        }
    }

    MovieEditsScreen(
        navController = navController,
        movieId = movieId,
        movie = viewModel.state.movie,
        onSaveClick = { viewModel.saveMovie(it) }
    )
}

@Composable
private fun MovieEditsScreen(
    navController: NavHostController,
    movieId: Int?,
    movie: MovieDB?,
    onSaveClick: (MovieDB) -> Unit,
) {
    if ((movieId ?: 0) > 0 && movie == null) return
    AppScaffold(navController, movie?.title) {
        val localFocusManager = LocalFocusManager.current
        var showYearDialog by remember { mutableStateOf(false) }

        var title by remember { mutableStateOf(movie?.title ?: "") }
        var titleRu by remember { mutableStateOf(movie?.titleRu ?: "") }
        var genres by remember { mutableStateOf(movie?.genres ?: "") }
        var releaseYear by remember { mutableStateOf(movie?.releaseYear ?: 0) }
        var comment by remember { mutableStateOf(movie?.comment ?: "") }
        var status by remember { mutableStateOf(movie?.status?.toString() ?: "") }
        var rating by remember { mutableStateOf(movie?.rating ?: 0) }

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
            Log.d("MovieEditsScreen", "title: $title")
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(title, { title = it }, stringResource(R.string.app_title))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(titleRu, { titleRu = it }, stringResource(R.string.app_title_ru))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(genres, { genres = it }, stringResource(R.string.app_genres))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextFieldRead(releaseYear.toString(), label = stringResource(R.string.app_year),
                onClick = { showYearDialog = true })
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(comment, { comment = it }, stringResource(R.string.app_comment))
            Spacer(modifier = Modifier.height(16.dp))

            RatingBar(rating = rating, onRatingChange = { rating = it })
            Spacer(modifier = Modifier.height(16.dp))

            AppStatusSpinner(
                initItem = movie?.status?.toString(),
                onSelect = { status = it.toString() }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSaveClick(
                        MovieDB(
                            id = movie?.id ?: 0,
                            title = title,
                            titleRu = titleRu,
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
        MovieEditsScreen(
            navController = rememberNavController(),
            movieId = 2,
            movie = MovieDB(
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