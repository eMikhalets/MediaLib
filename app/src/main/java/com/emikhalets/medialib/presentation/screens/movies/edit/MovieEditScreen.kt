package com.emikhalets.medialib.presentation.screens.movies.edit

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus.Companion.getText
import com.emikhalets.medialib.presentation.core.AppButton
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppSpinner
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.AppTextFieldDate
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.dialogs.YearEditDialog
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.formatYear
import com.emikhalets.medialib.utils.getRandomText
import com.emikhalets.medialib.utils.toast

@Composable
fun MovieEditScreen(
    navigateBack: () -> Unit,
    movieId: Long,
    viewModel: MovieEditViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var title by remember { mutableStateOf("") }
    var titleRu by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf("") }
    var year by remember { mutableStateOf(0.formatYear()) }
    var comment by remember { mutableStateOf("") }
    var watchStatus by remember { mutableStateOf(MovieWatchStatus.NONE) }
    var rating by remember { mutableStateOf(0) }
    var showYearDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getMovie(movieId)
    }

    LaunchedEffect(state.entity) {
        val entity = state.entity
        if (entity != null) {
            title = entity.movieEntity.title
            titleRu = entity.movieEntity.titleRu
            genres = entity.formatGenres()
            year = entity.movieEntity.year
            comment = entity.movieEntity.comment
            watchStatus = entity.movieEntity.watchStatus
            rating = entity.movieEntity.rating
        }
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            navigateBack()
        }
    }

    if (state.loading) {
        AppLoader()
    } else {
        val entity = state.entity
        if (entity == null) {
            AppTextFullScreen()
        } else {
            MovieEditScreen(
                entity = entity,
                title = title,
                titleRu = titleRu,
                genres = genres,
                year = year,
                comment = comment,
                rating = rating,
                watchStatus = watchStatus,
                onTitleChanged = { title = it },
                onTitleRuChanged = { titleRu = it },
                onGenresChanged = { genres = it },
                onCommentChanged = { comment = it },
                onRatingChanged = { rating = it },
                onWatchStatusChanged = { watchStatus = it },
                onYearClick = { showYearDialog = true },
                onSaveClick = {
                    viewModel.saveMovie(
                        title = title,
                        titleRu = titleRu,
                        genres = genres,
                        year = year,
                        comment = comment,
                        watchStatus = watchStatus,
                        rating = rating
                    )
                },
                onBackClick = navigateBack
            )
        }
    }

    if (showYearDialog) {
        YearEditDialog(
            year = year,
            onDismiss = { showYearDialog = false },
            onSaveClick = {
                year = it
                showYearDialog = false
            }
        )
    }
}

@Composable
private fun MovieEditScreen(
    entity: MovieFullEntity,
    title: String,
    titleRu: String,
    genres: String,
    year: Int,
    comment: String,
    rating: Int,
    watchStatus: MovieWatchStatus,
    onTitleChanged: (String) -> Unit,
    onTitleRuChanged: (String) -> Unit,
    onGenresChanged: (String) -> Unit,
    onCommentChanged: (String) -> Unit,
    onRatingChanged: (Int) -> Unit,
    onWatchStatusChanged: (MovieWatchStatus) -> Unit,
    onYearClick: () -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val focus = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) { detectTapGestures(onTap = { focus.clearFocus() }) }
    ) {
        AppTopBar(
            title = entity.movieEntity.title.ifEmpty { stringResource(R.string.movie_edit_new) },
            onNavigateBack = onBackClick
        )
        AppTextField(
            value = title,
            onValueChange = onTitleChanged,
            label = stringResource(R.string.movie_edit_title),
            modifier = Modifier.padding(top = 8.dp)
        )
        AppTextField(
            value = titleRu,
            onValueChange = onTitleRuChanged,
            label = stringResource(R.string.movie_edit_title_ru),
            modifier = Modifier.padding(top = 8.dp)
        )
        AppTextField(
            value = genres,
            onValueChange = onGenresChanged,
            label = stringResource(R.string.movie_edit_genres),
            modifier = Modifier.padding(top = 8.dp)
        )
        AppTextFieldDate(
            value = year.formatYear().toString(),
            label = stringResource(R.string.movie_edit_genres),
            onClick = onYearClick,
            modifier = Modifier.padding(top = 8.dp)
        )
        AppTextField(
            value = comment,
            onValueChange = onCommentChanged,
            label = stringResource(R.string.movie_edit_comment),
            modifier = Modifier.padding(top = 8.dp)
        )
        RatingBar(
            rating = rating,
            onRatingChange = onRatingChanged,
            modifier = Modifier.padding(top = 8.dp)
        )

        AppSpinner(
            selectedItem = watchStatus.getText(),
            items = MovieWatchStatus.getTextList(),
            onSelect = { onWatchStatusChanged(MovieWatchStatus.NONE) },
            modifier = Modifier.padding(top = 8.dp)
        )

        AppButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MovieEditScreen(
            entity = MovieFullEntity(
                movieEntity = MovieEntity(
                    id = 0,
                    title = "Movie name",
                    titleRu = "Movie name rus",
                    year = 2015,
                    rating = 4,
                    watchStatus = MovieWatchStatus.WATCH,
                    overview = getRandomText(20),
                    poster = "",
                    imdbRating = 6.4,
                    saveTimestamp = 0,
                    lastUpdateTimestamp = 0,
                    comment = getRandomText(20)
                ),
                genres = listOf(
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE),
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE),
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE)
                )
            ),
            title = "Movie name",
            titleRu = "Movie name rus",
            genres = "Action, Drama, Action, Drama",
            year = 2015,
            comment = getRandomText(20),
            rating = 4,
            watchStatus = MovieWatchStatus.WATCH,
            onTitleChanged = {},
            onTitleRuChanged = {},
            onGenresChanged = {},
            onCommentChanged = {},
            onRatingChanged = {},
            onWatchStatusChanged = {},
            onYearClick = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}