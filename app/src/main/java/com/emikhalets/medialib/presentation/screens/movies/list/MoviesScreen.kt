package com.emikhalets.medialib.presentation.screens.movies.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus.Companion.getIconRes
import com.emikhalets.medialib.presentation.core.AppAsyncImage
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.SearchBox
import com.emikhalets.medialib.presentation.core.TextPrimary
import com.emikhalets.medialib.presentation.core.TextSecondary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.toast

@Composable
fun MoviesScreen(
    navigateToMovieDetails: (movieId: Long) -> Unit,
    navigateToMovieEdit: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getMoviesList()
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    if (state.loading) {
        AppLoader()
    } else {
        MoviesScreen(
            query = query,
            moviesList = state.showedMovies,
            onQueryChange = {
                query = it
                viewModel.searchMovies(query)
            },
            onAddMovieClick = { navigateToMovieEdit() },
            onMovieClick = { movieId -> navigateToMovieDetails(movieId) },
            onBackClick = navigateBack
        )
    }
}

@Composable
private fun MoviesScreen(
    query: String,
    moviesList: List<MovieFullEntity>,
    onQueryChange: (String) -> Unit,
    onAddMovieClick: () -> Unit,
    onMovieClick: (movieId: Long) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(id = R.string.screen_title_movies),
            onNavigateBack = onBackClick
        )
        SearchBox(
            query = query,
            placeholder = stringResource(id = R.string.movies_search_placeholder),
            onQueryChange = onQueryChange,
            onAddClick = onAddMovieClick,
            modifier = Modifier.padding(8.dp)
        )

        if (moviesList.isEmpty()) {
            AppTextFullScreen(text = stringResource(id = R.string.movies_empty_list))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(moviesList) { entity ->
                    MovieBox(
                        entity = entity,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieBox(
    entity: MovieFullEntity,
    onMovieClick: (movieId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClick(entity.movieEntity.id) }
            .padding(vertical = 4.dp)
    ) {
        AppAsyncImage(
            url = entity.movieEntity.poster,
            height = 100.dp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextTitle(
                    text = entity.movieEntity.title,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                val statusIcon = entity.movieEntity.watchStatus.getIconRes()
                if (statusIcon != null) {
                    IconPrimary(drawableRes = statusIcon)
                }
            }
            if (entity.movieEntity.titleRu.isNotEmpty()) {
                TextSecondary(
                    text = entity.movieEntity.titleRu,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (entity.movieEntity.rating > 0) {
                RatingBar(
                    rating = entity.movieEntity.rating,
                    pointSize = 14.dp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            val info = entity.formatListItemInfo()
            if (info.isNotEmpty()) {
                TextPrimary(
                    text = info,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MoviesScreen(
            query = "",
            moviesList = emptyList(),
            onQueryChange = {},
            onAddMovieClick = {},
            onMovieClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    AppTheme {
        MovieBox(
            entity = MovieFullEntity(
                movieEntity = MovieEntity(
                    id = 0,
                    title = "Movie name",
                    titleRu = "Movie name",
                    year = 2015,
                    rating = 4,
                    watchStatus = MovieWatchStatus.WATCH,
                    overview = "",
                    poster = "",
                    imdbRating = 0.0,
                    saveTimestamp = 0,
                    lastUpdateTimestamp = 0,
                    comment = ""
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
            onMovieClick = {}
        )
    }
}