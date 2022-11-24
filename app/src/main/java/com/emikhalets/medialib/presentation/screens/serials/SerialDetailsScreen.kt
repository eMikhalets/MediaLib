package com.emikhalets.medialib.presentation.screens.serials

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.support.MenuIconEntity
import com.emikhalets.medialib.presentation.core.AppAsyncImage
import com.emikhalets.medialib.presentation.core.AppDetailsSection
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.DeleteDialog
import com.emikhalets.medialib.presentation.core.PosterDialog
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.navToMovieEdit
import com.emikhalets.medialib.presentation.navToSerialEdit
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun SerialDetailsScreen(
    navController: NavHostController,
    serialId: Int?,
    viewModel: SerialDetailsViewModel = hiltViewModel(),
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPosterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSerial(serialId)
    }

    LaunchedEffect(viewModel.state.deleted) {
        if (viewModel.state.deleted) {
            navController.popBackStack()
        }
    }

    SerialDetailsScreen(
        navController = navController,
        serial = viewModel.state.serial,
        onRatingChange = { viewModel.updateSerial(it) },
        onDeleteClick = { showDeleteDialog = true },
        onPosterClick = { showPosterDialog = true },
        onEditClick = { navController.navToSerialEdit(serialId) },
    )

    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = { showDeleteDialog = false },
            onDeleteClick = {
                viewModel.deleteSerial()
                showDeleteDialog = false
            }
        )
    }

    if (showPosterDialog) {
        PosterDialog(
            poster = viewModel.state.serial?.poster,
            onDismiss = { showPosterDialog = false },
            onOkClick = {
                viewModel.updateSerial(it)
                showPosterDialog = false
            }
        )
    }
}

@Composable
private fun SerialDetailsScreen(
    navController: NavHostController,
    serial: SerialDB?,
    onRatingChange: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onPosterClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    AppScaffold(
        navController = navController,
        title = serial?.title,
        actions = listOf(
            MenuIconEntity(Icons.Rounded.Edit) { onEditClick() },
            MenuIconEntity(Icons.Rounded.Delete) { onDeleteClick() }
        )
    ) {
        if (serial == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.app_loading_error),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            MovieItem(
                serial = serial,
                onRatingChange = onRatingChange,
                onPosterClick = onPosterClick,
            )
        }
    }
}

@Composable
private fun MovieItem(
    serial: SerialDB,
    onRatingChange: (Int) -> Unit,
    onPosterClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AppAsyncImage(
                data = serial.poster,
                height = 150.dp,
                onClick = { onPosterClick() }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = serial.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                val localeTitle = serial.getLocaleTitle()
                if (localeTitle.isNotEmpty()) {
                    Text(
                        text = localeTitle,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = serial.releaseYear.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                RatingBar(
                    rating = serial.rating,
                    onRatingChange = onRatingChange,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        AppDetailsSection(
            header = stringResource(R.string.app_comment),
            content = serial.comment
        )
        AppDetailsSection(
            header = stringResource(R.string.app_overview),
            content = serial.overview
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.app_genres_value, serial.genres),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.serials_seasons_value, serial.seasons),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SerialDetailsScreen(
            navController = rememberNavController(),
            serial = SerialDB(
                title = "Long Spider-man",
                titleRu = "Человек-паук",
                genres = "Action, Drama",
                releaseYear = 2018,
                seasons = 4,
                rating = 4,
                comment = "Test comment overview overview overview overview overview overview overview overview",
                overview = "overview overview overview overview overview overview overview overview overview overview"
            ),
            onRatingChange = {},
            onDeleteClick = {},
            onPosterClick = {},
            onEditClick = {},
        )
    }
}