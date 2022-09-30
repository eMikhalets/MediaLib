package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.GenresHelper
import com.emikhalets.medialib.utils.ImagePathBuilder
import com.emikhalets.medialib.utils.items
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }
    val movies = viewModel.state.moviesFlow.collectAsLazyPagingItems()

    SearchScreen(
        query = query,
        movies = movies,
        genresHelper = viewModel.genreHelper,
        onQueryChange = {
            query = it
            viewModel.search(query)
        },
        onMovieClick = {},
        onMovieViewedClick = {},
    )
}

@Composable
fun SearchScreen(
    query: String,
    movies: LazyPagingItems<MovieSearchResult>,
    genresHelper: GenresHelper,
    onQueryChange: (String) -> Unit,
    onMovieClick: (MovieSearchResult) -> Unit,
    onMovieViewedClick: (MovieSearchResult) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
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

        if (movies.itemCount == 0) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.search_no_movies),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(movies) { movie ->
                    movie?.let {
                        MovieItem(movie, genresHelper, onMovieClick, onMovieViewedClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieSearchResult,
    genresHelper: GenresHelper,
    onMovieClick: (MovieSearchResult) -> Unit,
    onMovieViewedClick: (MovieSearchResult) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie) }
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ImagePathBuilder().buildPosterPath(movie.poster))
                    .crossfade(true)
                    .transformations(RoundedCornersTransformation(getImageCornerRadius()))
                    .error(R.drawable.ph_movie)
                    .build(),
                contentDescription = "movie poster",
                placeholder = painterResource(R.drawable.ph_movie),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)

            )
            movie.voteAverage?.let { rating ->
                Text(
                    text = rating.toString(),
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 8.dp)
                )
            }
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "adding movie icon",
                modifier = Modifier
                    .clickable { onMovieViewedClick(movie) }
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp)
            )
        }

        Text(
            text = movie.title ?: "No title",
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        )
        Text(
            text = formatMovieFooter(movie, genresHelper),
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun getImageCornerRadius(): Float {
    return 8 * LocalContext.current.resources.displayMetrics.density
}

private fun formatMovieFooter(
    movie: MovieSearchResult,
    genresHelper: GenresHelper,
): String {
    val genre = genresHelper.getMovieGenre(movie.genres?.firstOrNull())
    val year = movie.releaseDate?.split("-")?.firstOrNull()
    return if (year?.trim().isNullOrEmpty()) {
        genre
    } else {
        "$genre, $year"
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SearchScreen(
            query = "Some movie",
            movies = getPreviewMoviesFlow(),
            genresHelper = GenresHelper(),
            onQueryChange = {},
            onMovieClick = {},
            onMovieViewedClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenEmptyPreview() {
    AppTheme {
        SearchScreen(
            query = "",
            movies = getPreviewEmptyFlow(),
            genresHelper = GenresHelper(),
            onQueryChange = {},
            onMovieClick = {},
            onMovieViewedClick = {},
        )
    }
}

@Composable
private fun getPreviewEmptyFlow(): LazyPagingItems<MovieSearchResult> {
    return flowOf(PagingData.empty<MovieSearchResult>()).collectAsLazyPagingItems()
}

@Composable
private fun getPreviewMoviesFlow(): LazyPagingItems<MovieSearchResult> {
    return flowOf(
        PagingData.from(
            listOf(
                MovieSearchResult(
                    id = 1,
                    title = "Spider-man",
                    genres = listOf(1, 2, 3),
                    poster = "",
                    releaseDate = "2012-04-25",
                    voteAverage = 5.6
                ),
                MovieSearchResult(
                    id = 1,
                    title = "Spider-man 2",
                    genres = listOf(1, 2, 3),
                    poster = "",
                    releaseDate = "2012-04-25",
                    voteAverage = 5.6
                ),
                MovieSearchResult(
                    id = 1,
                    title = "Spider-man 3",
                    genres = listOf(1, 2, 3),
                    poster = "",
                    releaseDate = "2012-04-25",
                    voteAverage = 5.6
                ),
                MovieSearchResult(
                    id = 1,
                    title = "Avengers",
                    genres = listOf(1, 2, 3),
                    poster = "",
                    releaseDate = "2012-04-25",
                    voteAverage = 5.6
                ),
                MovieSearchResult(
                    id = 1,
                    title = "Venom",
                    genres = listOf(1, 2, 3),
                    poster = "",
                    releaseDate = "2012-04-25",
                    voteAverage = 5.6
                ),
                MovieSearchResult(
                    id = 1,
                    title = "Naked gun",
                    genres = listOf(1, 2, 3),
                    poster = "",
                    releaseDate = "2012-04-25",
                    voteAverage = 5.6
                ),
            )
        )
    ).collectAsLazyPagingItems()
}