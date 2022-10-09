package com.emikhalets.medialib.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.presentation.navToSavedMovie
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.ImagePathBuilder
import com.emikhalets.medialib.utils.px

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getSavedMovies(query)
    }

    MainScreen(
        query = query,
        movies = viewModel.state.movies,
        onQueryChange = {
            query = it
            viewModel.getSavedMovies(query)
        },
        onMovieClick = { navController.navToSavedMovie(it) },
    )
}

@Composable
private fun MainScreen(
    query: String,
    movies: List<MovieDB>,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { localFocusManager.clearFocus() },
                    onTap = { localFocusManager.clearFocus() }
                )
            }
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = { Icon(Icons.Rounded.Search, "search icon") },
            placeholder = { Text(stringResource(R.string.search_query_placeholder)) },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        if (movies.isEmpty()) {
            val text = if (query.isEmpty()) R.string.main_no_movies
            else R.string.main_no_movies_query

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(text),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(movies) { movie ->
                    MovieItem(movie, onMovieClick)
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieDB,
    onMovieClick: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ImagePathBuilder().buildPosterPath(movie.poster))
                .crossfade(true)
                .transformations(RoundedCornersTransformation(8.px))
                .error(R.drawable.ph_poster)
                .build(),
            contentDescription = "movie poster",
            placeholder = painterResource(R.drawable.ph_poster),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(150.dp)
                .padding(8.dp)

        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        ) {
            Text(
                text = movie.title.ifEmpty { stringResource(R.string.app_no_title) },
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            if (movie.originalTitle.isNotEmpty()) {
                Text(
                    text = movie.originalTitle,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.main_genres, movie.genres),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.main_date, movie.releaseDate),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.main_runtime, movie.runtime),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MainScreen(
            query = "",
            movies = getPreviewMovies(),
            onQueryChange = {},
            onMovieClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenEmptyPreview() {
    AppTheme {
        MainScreen(
            query = "",
            movies = emptyList(),
            onQueryChange = {},
            onMovieClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenQueryEmptyPreview() {
    AppTheme {
        MainScreen(
            query = "spider",
            movies = emptyList(),
            onQueryChange = {},
            onMovieClick = {},
        )
    }
}

@Composable
private fun getPreviewMovies(): List<MovieDB> {
    return listOf(
        MovieDB(
            id = 0,
            title = "Long long long long long long long long long Spider-man",
            budget = 0,
            backdrop = "",
            genres = "Action, Drama",
            imdbId = "",
            originalTitle = "Original title",
            overview = "",
            poster = "",
            releaseDate = "2014-05-06",
            revenue = 0,
            runtime = 122,
            status = "",
            tagline = "",
            voteAverage = 0.0,
        ),
        MovieDB(
            id = 0,
            title = "Spider-man",
            budget = 0,
            backdrop = "",
            genres = "Action, Drama",
            imdbId = "",
            originalTitle = "Original title",
            overview = "",
            poster = "",
            releaseDate = "2014-05-06",
            revenue = 0,
            runtime = 122,
            status = "",
            tagline = "",
            voteAverage = 0.0,
        ),
        MovieDB(
            id = 0,
            title = "Spider-man",
            budget = 0,
            backdrop = "",
            genres = "Action, Drama",
            imdbId = "",
            originalTitle = "Original title",
            overview = "",
            poster = "",
            releaseDate = "2014-05-06",
            revenue = 0,
            runtime = 122,
            status = "",
            tagline = "",
            voteAverage = 0.0,
        ),
        MovieDB(
            id = 0,
            title = "Spider-man",
            budget = 0,
            backdrop = "",
            genres = "Action, Drama",
            imdbId = "",
            originalTitle = "Original title",
            overview = "",
            poster = "",
            releaseDate = "2014-05-06",
            revenue = 0,
            runtime = 122,
            status = "",
            tagline = "",
            voteAverage = 0.0,
        ),
        MovieDB(
            id = 0,
            title = "Spider-man",
            budget = 0,
            backdrop = "",
            genres = "Action, Drama",
            imdbId = "",
            originalTitle = "Original title",
            overview = "",
            poster = "",
            releaseDate = "2014-05-06",
            revenue = 0,
            runtime = 122,
            status = "",
            tagline = "",
            voteAverage = 0.0,
        ),
        MovieDB(
            id = 0,
            title = "Spider-man",
            budget = 0,
            backdrop = "",
            genres = "Action, Drama",
            imdbId = "",
            originalTitle = "Original title",
            overview = "",
            poster = "",
            releaseDate = "2014-05-06",
            revenue = 0,
            runtime = 122,
            status = "",
            tagline = "",
            voteAverage = 0.0,
        ),
    )
}