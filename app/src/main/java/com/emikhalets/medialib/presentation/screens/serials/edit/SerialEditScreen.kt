package com.emikhalets.medialib.presentation.screens.serials.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus.Companion.getText
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppSpinner
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.ButtonPrimary
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.dialogs.YearEditDialog
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.formatYear
import com.emikhalets.medialib.utils.getRandomText
import com.emikhalets.medialib.utils.toast

@Composable
fun SerialEditScreen(
    navigateBack: () -> Unit,
    serialId: Long,
    viewModel: SerialEditViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var title by remember { mutableStateOf("") }
    var titleRu by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf("") }
    var year by remember { mutableStateOf(0.formatYear()) }
    var comment by remember { mutableStateOf("") }
    var watchStatus by remember { mutableStateOf(SerialWatchStatus.NONE) }
    var rating by remember { mutableStateOf(0) }
    var overview by remember { mutableStateOf("") }
    var showYearDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSerial(serialId)
    }

    LaunchedEffect(state.entity) {
        val entity = state.entity
        if (entity != null) {
            title = entity.serialEntity.title
            titleRu = entity.serialEntity.titleRu
            genres = entity.formatGenres()
            year = entity.serialEntity.year
            comment = entity.serialEntity.comment
            watchStatus = entity.serialEntity.watchStatus
            rating = entity.serialEntity.rating
            overview = entity.serialEntity.overview
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

    LaunchedEffect(state.saved) {
        if (state.saved) {
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
            SerialEditScreen(
                entity = entity,
                title = title,
                titleRu = titleRu,
                genres = genres,
                year = year,
                comment = comment,
                rating = rating,
                overview = overview,
                watchStatus = watchStatus,
                onTitleChanged = { title = it },
                onTitleRuChanged = { titleRu = it },
                onGenresChanged = { genres = it },
                onCommentChanged = { comment = it },
                onRatingChanged = { rating = it },
                onOverviewChanged = { overview = it },
                onWatchStatusChanged = { watchStatus = SerialWatchStatus.getStatus(context, it) },
                onYearClick = { showYearDialog = true },
                onSaveClick = {
                    viewModel.saveSerial(
                        title = title,
                        titleRu = titleRu,
                        genres = genres,
                        year = year,
                        comment = comment,
                        watchStatus = watchStatus,
                        rating = rating,
                        overview = overview
                    )
                },
                onBackClick = navigateBack
            )
        }
    }

    if (showYearDialog) {
        YearEditDialog(
            year = year,
            onDismiss = { showYearDialog = false },
            onSaveClick = {
                year = it
                showYearDialog = false
            }
        )
    }
}

@Composable
private fun SerialEditScreen(
    entity: SerialFullEntity,
    title: String,
    titleRu: String,
    genres: String,
    year: Int,
    comment: String,
    rating: Int,
    overview: String,
    watchStatus: SerialWatchStatus,
    onTitleChanged: (String) -> Unit,
    onTitleRuChanged: (String) -> Unit,
    onGenresChanged: (String) -> Unit,
    onCommentChanged: (String) -> Unit,
    onRatingChanged: (Int) -> Unit,
    onOverviewChanged: (String) -> Unit,
    onWatchStatusChanged: (String) -> Unit,
    onYearClick: () -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val focus = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = entity.serialEntity.title.ifEmpty { stringResource(R.string.serial_edit_new) },
            onNavigateBack = onBackClick
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
                .pointerInput(Unit) { detectTapGestures(onTap = { focus.clearFocus() }) }
        ) {
            AppTextField(
                value = title,
                onValueChange = onTitleChanged,
                label = stringResource(R.string.serial_edit_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            AppTextField(
                value = titleRu,
                onValueChange = onTitleRuChanged,
                label = stringResource(R.string.serial_edit_title_ru),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            AppTextField(
                value = genres,
                onValueChange = onGenresChanged,
                label = stringResource(R.string.serial_edit_genres),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            AppTextField(
                value = year.formatYear().toString(),
                onValueChange = {},
                label = stringResource(R.string.serial_edit_year),
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clickable { onYearClick() }
            )
            AppSpinner(
                selectedItem = watchStatus.getText(),
                items = SerialWatchStatus.getTextList(),
                label = stringResource(R.string.serial_edit_watch_status),
                onSelect = { onWatchStatusChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            AppTextField(
                value = overview,
                onValueChange = onOverviewChanged,
                label = stringResource(R.string.serial_edit_overview),
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            AppTextField(
                value = comment,
                onValueChange = onCommentChanged,
                label = stringResource(R.string.serial_edit_comment),
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            RatingBar(
                rating = rating,
                onRatingChange = onRatingChanged,
                modifier = Modifier.padding(top = 16.dp)
            )
            ButtonPrimary(
                text = stringResource(R.string.serial_edit_save),
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SerialEditScreen(
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
                    comment = getRandomText(20),
                    runtime = "",
                    awards = ""
                ),
                genres = listOf(
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL),
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL),
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL)
                ),
                ratings = emptyList(),
                crew = emptyList()
            ),
            title = "Serial name",
            titleRu = "Serial name rus",
            genres = "Action, Drama, Action, Drama",
            year = 2015,
            comment = getRandomText(20),
            rating = 4,
            overview = getRandomText(20),
            watchStatus = SerialWatchStatus.WATCH,
            onTitleChanged = {},
            onTitleRuChanged = {},
            onGenresChanged = {},
            onCommentChanged = {},
            onRatingChanged = {},
            onOverviewChanged = {},
            onWatchStatusChanged = {},
            onYearClick = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}