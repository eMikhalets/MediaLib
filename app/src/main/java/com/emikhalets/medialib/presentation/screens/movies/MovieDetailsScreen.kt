package com.emikhalets.medialib.presentation.screens.movies

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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
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
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.px

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    movieId: Int?,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getSavedMovie(movieId)
    }

    LaunchedEffect(viewModel.state.movie) {
        val movie = viewModel.state.movie
        comment = movie?.comment ?: ""
        rating = movie?.rating ?: 0
    }

    MovieDetailsScreen(
        navController = navController,
        movie = viewModel.state.movie,
        comment = comment,
        rating = rating,
        onCommentChange = { comment = it },
        onRatingChange = { rating = it },
        onUpdateClick = viewModel::updateMovie,
        onDeleteClick = viewModel::deleteMovie,
    )
}

@Composable
private fun MovieDetailsScreen(
    navController: NavHostController,
    movie: MovieEntity?,
    comment: String,
    rating: Int,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    AppScaffold(navController, movie?.title) {
        if (movie == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.movie_local_load_error),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            MovieItem(
                movie = movie,
                comment = comment,
                rating = rating,
                onCommentChange = onCommentChange,
                onRatingChange = onRatingChange,
                onUpdateClick = onUpdateClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieEntity,
    comment: String,
    rating: Int,
    onCommentChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
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
                RatingBar(
                    rating = rating,
                    onRatingChange = onRatingChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.movie_local_rating,
                        movie.voteAverage.toString()),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.movie_local_runtime, movie.runtime.toString()),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.movie_local_date, movie.releaseDate),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onUpdateClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = stringResource(R.string.movie_local_save))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onDeleteClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = stringResource(R.string.movie_local_delete))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title.ifEmpty { stringResource(R.string.app_no_title) },
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (movie.originalTitle.isNotEmpty()) {
            Text(
                text = movie.originalTitle,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.movie_local_genres, movie.genres),
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (movie.overview.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.movie_local_overview),
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
        if (movie.budget > 0 && movie.revenue > 0) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.movie_local_budget, movie.budget),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.movie_local_revenue, movie.revenue),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = comment,
            onValueChange = onCommentChange,
            label = { Text(stringResource(R.string.movie_local_comment)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MovieDetailsScreen(
            navController = rememberNavController(),
            movie = MovieEntity(
                id = 1,
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
                voteAverage = 0.0
            ),
            comment = "Test comment",
            rating = 4,
            onCommentChange = {},
            onRatingChange = {},
            onUpdateClick = {},
            onDeleteClick = {},
        )
    }
}