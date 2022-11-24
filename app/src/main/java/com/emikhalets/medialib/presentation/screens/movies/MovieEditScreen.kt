package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.MovieStatus
import com.emikhalets.medialib.utils.toSafeInt
import com.emikhalets.medialib.utils.toSafeLong

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
        movie = viewModel.state.movie,
        onSaveClick = { viewModel.saveMovie(it) }
    )
}

@Composable
private fun MovieEditsScreen(
    navController: NavHostController,
    movie: MovieDB?,
    onSaveClick: (MovieDB) -> Unit,
) {
    AppScaffold(navController, movie?.title) {
        var title by remember { mutableStateOf(movie?.title ?: movie?.title ?: "") }
        var titleRu by remember { mutableStateOf(movie?.titleRu ?: "") }
        var genres by remember { mutableStateOf(movie?.genres ?: "") }
        var overview by remember { mutableStateOf(movie?.overview ?: "") }
        var releaseYear by remember { mutableStateOf(movie?.releaseYear?.toString() ?: "") }
        var budget by remember { mutableStateOf(movie?.budget?.toString() ?: "") }
        var revenue by remember { mutableStateOf(movie?.revenue?.toString() ?: "") }
        var comment by remember { mutableStateOf(movie?.comment ?: "") }
        var status by remember { mutableStateOf(movie?.status?.toString() ?: "") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AppTextField(title, { title = it }, stringResource(R.string.app_title))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(titleRu, { titleRu = it }, stringResource(R.string.app_title_ru))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(genres, { genres = it }, stringResource(R.string.app_genres))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(overview, { overview = it }, stringResource(R.string.app_overview))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(releaseYear, { releaseYear = it }, stringResource(R.string.app_year),
                KeyboardType.Number)
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(budget, { budget = it }, stringResource(R.string.app_budget),
                KeyboardType.Number)
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(revenue, { revenue = it }, stringResource(R.string.app_revenue),
                KeyboardType.Number)
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(comment, { comment = it }, stringResource(R.string.app_comment))
            Spacer(modifier = Modifier.height(8.dp))

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
                            overview = overview,
                            releaseYear = releaseYear.toSafeInt(),
                            budget = budget.toSafeLong(),
                            revenue = revenue.toSafeLong(),
                            comment = comment,
                            status = MovieStatus.get(status)
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.app_save))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MovieEditsScreen(
            navController = rememberNavController(),
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