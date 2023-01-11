package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.movies.getIconRes
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.core.RatingRow
import com.emikhalets.medialib.presentation.core.SearchBox
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.px

@Composable
fun MoviesScreen(
    navigateToMovieDetails: (movieId: Long) -> Unit,
    navigateToMovieEdit: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getMoviesList()
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
            onMovieClick = { movieId -> navigateToMovieDetails(movieId) }
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
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBox(
            query = query,
            placeholder = stringResource(id = R.string.movies_search_placeholder),
            onQueryChange = onQueryChange,
            onAddClick = onAddMovieClick
        )

        if (moviesList.isEmpty()) {
            AppTextFullScreen(text = stringResource(id = R.string.movies_empty_list))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
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
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(entity.movieEntity.id) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(entity.movieEntity.poster)
                .crossfade(true)
                .transformations(RoundedCornersTransformation(8.px))
                .error(R.drawable.ph_poster)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.ph_poster),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(70.dp, 95.dp)
                .padding(end = 8.dp)

        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = entity.movieEntity.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                val statusIcon = entity.movieEntity.watchStatus.getIconRes()
                if (statusIcon != null) {
                    IconPrimary(drawableRes = statusIcon)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val info = buildString {
                    append(entity.movieEntity.year.toString())
                    if (entity.genres.isNotEmpty()) {
                        append(", ${entity.genres.joinToString { it.name }}")
                    }
                }
                Text(
                    text = info,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (entity.movieEntity.rating > 0) {
                    RatingRow(entity.movieEntity.rating)
                }
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
            onMovieClick = {}
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
                    titleRu = "",
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