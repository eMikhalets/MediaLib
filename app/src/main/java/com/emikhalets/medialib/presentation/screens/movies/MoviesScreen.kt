package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
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
import com.emikhalets.medialib.data.entity.views.MovieEntity
import com.emikhalets.medialib.presentation.core.AddItemDialog
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToMovieDetails
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.ImagePathBuilder
import com.emikhalets.medialib.utils.px

@Composable
fun MoviesScreen(
    navController: NavHostController,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSavedMovies(query)
    }

    MoviesScreen(
        navController = navController,
        query = query,
        movies = viewModel.state.movies,
        showAddDialog = showAddDialog,
        onAddClick = { name, year, comment ->
            showAddDialog = false
            viewModel.addMovie(name, year, comment)
        },
        onAddDialogVisible = { showAddDialog = it },
        onQueryChange = {
            query = it
            viewModel.getSavedMovies(query)
        },
        onMovieClick = {
            navController.navToMovieDetails(it)
        },
    )
}

@Composable
private fun MoviesScreen(
    navController: NavHostController,
    query: String,
    movies: List<MovieEntity>,
    showAddDialog: Boolean,
    onAddClick: (String, String, String) -> Unit,
    onAddDialogVisible: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    AppScaffold(navController) {
        RootScreenList(
            query = query,
            list = movies,
            searchPlaceholder = stringResource(R.string.movies_query_placeholder),
            onAddClick = { onAddDialogVisible(true) },
            onQueryChange = onQueryChange,
            onItemClick = onMovieClick
        ) { item ->
            MovieItem(item as MovieEntity, onMovieClick)
        }

        if (showAddDialog) {
            AddItemDialog(
                onDismiss = { onAddDialogVisible(false) },
                onAddClick = onAddClick
            )
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieEntity,
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
                .padding(8.dp)
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
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.movie_local_genres, movie.genres),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.movie_local_date, movie.releaseDate),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.movie_local_runtime, movie.runtime),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = ""
                    )
                    Text(
                        text = movie.rating.toString(),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
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
            navController = rememberNavController(),
            query = "",
            movies = listOf(),
            showAddDialog = true,
            onAddClick = { _, _, _ -> },
            onAddDialogVisible = {},
            onQueryChange = {},
            onMovieClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPreview() {
    AppTheme {
        MovieItem(
            movie = MovieEntity(
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
            onMovieClick = {},
        )
    }
}