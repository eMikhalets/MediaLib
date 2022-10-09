package com.emikhalets.medialib.presentation.screens.searched_movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
import com.emikhalets.medialib.data.entity.support.GenreData
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.GenresHelper
import com.emikhalets.medialib.utils.ImagePathBuilder
import com.emikhalets.medialib.utils.px

@Composable
fun SearchedMovieScreen(
    navController: NavHostController,
    movieId: Int,
    viewModel: SearchedMovieViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getMovie(movieId)
    }

    LaunchedEffect(viewModel.state.movie) {
        if (viewModel.state.movie != null) viewModel.isAlreadySaved(movieId)
    }

    SearchedMovieScreen(
        movie = viewModel.state.movie,
        isAlreadySaved = viewModel.state.isAlreadySaved,
        onSaveMovieClick = { viewModel.saveMovie() },
    )
}

@Composable
private fun SearchedMovieScreen(
    movie: MovieDetailsResponse?,
    isAlreadySaved: Boolean,
    onSaveMovieClick: () -> Unit,
) {
    if (movie == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.searched_no_movie),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(ImagePathBuilder().buildBackdropPath(movie.backdrop))
                    .crossfade(true)
                    .error(R.drawable.ph_backdrop)
                    .build(),
                contentDescription = "movie backdrop",
                placeholder = painterResource(R.drawable.ph_backdrop),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
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
                        .height(170.dp)
                        .padding(16.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(vertical = 16.dp)
                        .padding(end = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.searched_rating,
                            movie.voteAverage.toString()),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.searched_runtime,
                            movie.runtime.toString()),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.searched_date,
                            movie.releaseDate.toString()),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (!isAlreadySaved) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onSaveMovieClick() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(text = stringResource(R.string.searched_save))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title ?: stringResource(R.string.app_no_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (movie.originalTitle != null) {
                Text(
                    text = "${movie.originalTitle} + (${movie.originalLanguage})",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.searched_genres,
                    GenresHelper.remoteToString(movie.genres)),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (movie.overview != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.searched_overview),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Text(
                    text = movie.overview,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            if (movie.budget != null && movie.revenue != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.searched_budget, movie.budget),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.searched_revenue, movie.revenue),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SearchedMovieScreen(
            movie = getPreviewMovieDetails(),
            isAlreadySaved = false,
            onSaveMovieClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenEmptyPreview() {
    AppTheme {
        SearchedMovieScreen(
            movie = null,
            isAlreadySaved = true,
            onSaveMovieClick = {}
        )
    }
}

@Composable
private fun getPreviewMovieDetails(): MovieDetailsResponse {
    return MovieDetailsResponse(
        id = 0,
        title = "Some movie",
        budget = 123456789,
        backdrop = "",
        genres = listOf(
            GenreData(0, "genre1"),
            GenreData(0, "genre2"),
            GenreData(0, "genre3"),
            GenreData(0, "genre4")
        ),
        imdbId = "",
        originalTitle = "Оригинальный титул",
        overview = LoremIpsum(20).values.joinToString(),
        poster = "",
        releaseDate = "2012-05-12",
        revenue = 12345678,
        runtime = 120,
        status = "",
        tagline = "Some tagline of movie",
        voteAverage = 7.8,
    )
}