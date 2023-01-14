package com.emikhalets.medialib.presentation.screens.serials.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import com.emikhalets.medialib.domain.entities.utils.MenuIconEntity
import com.emikhalets.medialib.presentation.core.AppAsyncImage
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.DetailsSection
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.TextPrimary
import com.emikhalets.medialib.presentation.core.TextSecondary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.dialogs.DeleteFromDbDialog
import com.emikhalets.medialib.presentation.dialogs.PosterEditDialog
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.getRandomText
import com.emikhalets.medialib.utils.toast

@Composable
fun SerialDetailsScreen(
    navigateToSerialEdit: (serialId: Long) -> Unit,
    navigateBack: () -> Unit,
    serialId: Long,
    viewModel: SerialDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var poster by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPosterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSerial(serialId)
    }

    LaunchedEffect(state.entity) {
        val entity = state.entity
        if (entity != null) {
            poster = entity.serialEntity.poster
            rating = entity.serialEntity.rating
        }
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            navigateBack()
        }
    }

    if (state.loading) {
        AppLoader()
    } else {
        val entity = state.entity
        if (entity == null) {
            AppTextFullScreen()
        } else {
            DetailsScreen(
                entity = entity,
                poster = poster,
                rating = rating,
                onRatingChange = {
                    rating = it
                    viewModel.updateSerialRating(rating)
                },
                onPosterClick = { showPosterDialog = true },
                onSerialEditClick = navigateToSerialEdit,
                onBackClick = navigateBack,
                onDeleteClick = { showDeleteDialog = true }
            )
        }
    }

    if (showDeleteDialog) {
        DeleteFromDbDialog(
            onDismiss = { showDeleteDialog = false },
            onDeleteClick = {
                showDeleteDialog = false
                viewModel.deleteSerial()
            }
        )
    }

    if (showPosterDialog) {
        PosterEditDialog(
            url = state.entity?.serialEntity?.poster ?: "",
            onDismiss = { showPosterDialog = false },
            onSaveClick = { url ->
                showPosterDialog = false
                poster = url
                viewModel.updateSerialPoster(poster)
            }
        )
    }
}

@Composable
private fun DetailsScreen(
    entity: SerialFullEntity,
    poster: String,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    onPosterClick: () -> Unit,
    onSerialEditClick: (serialId: Long) -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(id = R.string.screen_title_serial_details),
            onNavigateBack = onBackClick,
            actions = listOf(
                MenuIconEntity(R.drawable.ic_round_edit_24) {
                    onSerialEditClick(entity.serialEntity.id)
                },
                MenuIconEntity(R.drawable.ic_round_delete_24) {
                    onDeleteClick()
                }
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                AppAsyncImage(
                    url = poster,
                    height = 170.dp,
                    onClick = { onPosterClick() }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(8.dp)
                        .padding(start = 8.dp)
                ) {
                    TextTitle(
                        text = entity.serialEntity.title,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (entity.serialEntity.titleRu.isNotEmpty()) {
                        TextSecondary(
                            text = entity.serialEntity.titleRu,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    val year = entity.serialEntity.year
                    if (year > 0) {
                        TextPrimary(
                            text = year.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                    RatingBar(
                        rating = rating,
                        onRatingChange = onRatingChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
            DetailsSection(
                header = stringResource(R.string.serial_details_genres),
                content = entity.formatGenres(),
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSection(
                header = stringResource(R.string.serial_details_overview),
                content = entity.serialEntity.overview,
                modifier = Modifier.padding(top = 8.dp)
            )
            DetailsSection(
                header = stringResource(R.string.serial_details_comment),
                content = entity.serialEntity.comment,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        DetailsScreen(
            entity = SerialFullEntity(
                serialEntity = SerialEntity(
                    id = 0,
                    title = "Serial name",
                    titleRu = "Serial name rus",
                    year = 2015,
                    rating = 4,
                    watchStatus = SerialWatchStatus.WATCH,
                    overview = getRandomText(20),
                    poster = "",
                    saveTimestamp = 0,
                    lastUpdateTimestamp = 0,
                    comment = getRandomText(20)
                ),
                genres = listOf(
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL),
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL),
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL)
                )
            ),
            poster = "",
            rating = 4,
            onRatingChange = {},
            onPosterClick = {},
            onSerialEditClick = {},
            onBackClick = {},
            onDeleteClick = {}
        )
    }
}