package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.presentation.core.AddItemDialog
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToMovieDetails
import com.emikhalets.medialib.presentation.theme.AppTheme
import java.util.*

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
    movies: List<MovieDB>,
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
            MovieItem(item as MovieDB, onMovieClick)
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
    movie: MovieDB,
    onMovieClick: (Int) -> Unit,
) {
    RootListItem(
        item = movie,
        onItemClick = onMovieClick
    ) {
        Text(
            text = stringResource(R.string.app_genres_value, movie.genres),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.app_runtime_runtime_value, movie.runtime),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
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
            showAddDialog = false,
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
            movie = MovieDB(
                id = 0,
                title = "Long long long long long long long long long Spider-man",
                budget = 0,
                backdrop = "",
                genres = "Action, Drama",
                imdbId = "",
                originalTitle = "Original title",
                overview = "",
                poster = "",
                releaseDate = Calendar.getInstance().timeInMillis,
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