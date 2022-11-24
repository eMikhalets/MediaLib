package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToMovieDetails
import com.emikhalets.medialib.presentation.navToMovieEdit
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus

@Composable
fun MoviesScreen(
    navController: NavHostController,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getSavedMovies(query)
    }

    MoviesScreen(
        navController = navController,
        query = query,
        movies = viewModel.state.movies,
        onAddClick = {
            navController.navToMovieEdit(null)
        },
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
    onAddClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
) {
    AppScaffold(navController) {
        RootScreenList(
            query = query,
            list = movies,
            searchPlaceholder = stringResource(R.string.movies_query_placeholder),
            onAddClick = onAddClick,
            onQueryChange = onQueryChange,
            onItemClick = onMovieClick
        ) { item ->
            MovieItem(item as MovieDB, onMovieClick)
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
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MoviesScreen(
            navController = rememberNavController(),
            query = "",
            movies = listOf(),
            onAddClick = {},
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
                title = "Long long long long Spider-man",
                genres = "Action, Drama, Action, Drama, Action, Drama",
                releaseYear = 2015,
                rating = 4,
                status = ItemStatus.WATCH
            ),
            onMovieClick = {},
        )
    }
}