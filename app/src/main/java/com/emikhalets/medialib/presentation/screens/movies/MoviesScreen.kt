package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.presentation.core.AppDialog
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RootListItem
import com.emikhalets.medialib.presentation.core.RootScreenList
import com.emikhalets.medialib.presentation.navToMovieDetails
import com.emikhalets.medialib.presentation.theme.AppTheme

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
        onAddClick = {
            showAddDialog = false
            viewModel.addMovie(it)
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
    onAddClick: (MovieDB) -> Unit,
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
            AddMovieDialog(
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

@Composable
private fun AddMovieDialog(
    onDismiss: () -> Unit,
    onAddClick: (MovieDB) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var titleRu by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf("") }

    AppDialog(
        label = stringResource(id = R.string.movies_add_title),
        onDismiss = { onDismiss() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.add_new_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = titleRu,
                onValueChange = { titleRu = it },
                label = { Text(stringResource(id = R.string.add_new_title_ru)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.add_new_year)) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = genres,
                onValueChange = { genres = it },
                label = { Text(stringResource(R.string.app_genres)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(stringResource(R.string.app_comment)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            IconButton(
                onClick = {
                    onAddClick(
                        MovieDB(
                            title = title,
                            titleRu = titleRu,
                            releaseYear = year.toInt(),
                            comment = comment,
                            genres = genres
                        )
                    )
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
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
            showAddDialog = false,
            onAddClick = {},
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
                title = "Long long long long long long long long long Spider-man",
                genres = "Action, Drama",
                releaseYear = 2015,
                runtime = 122,
            ),
            onMovieClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddMovieDialogPreview() {
    AppTheme {
        AddMovieDialog(
            onDismiss = {},
            onAddClick = {}
        )
    }
}