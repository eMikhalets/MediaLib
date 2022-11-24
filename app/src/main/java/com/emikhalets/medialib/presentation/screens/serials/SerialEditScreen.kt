package com.emikhalets.medialib.presentation.screens.serials

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppStatusSpinner
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus
import com.emikhalets.medialib.utils.toSafeInt

@Composable
fun MovieEditsScreen(
    navController: NavHostController,
    serialId: Int?,
    viewModel: SerialEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getSerial(serialId)
    }

    LaunchedEffect(viewModel.state.saved) {
        if (viewModel.state.saved) {
            navController.popBackStack()
        }
    }

    MovieEditsScreen(
        navController = navController,
        serial = viewModel.state.serial,
        onSaveClick = { viewModel.saveSerial(it) }
    )
}

@Composable
private fun MovieEditsScreen(
    navController: NavHostController,
    serial: SerialDB?,
    onSaveClick: (SerialDB) -> Unit,
) {
    AppScaffold(navController, serial?.title) {
        val localFocusManager = LocalFocusManager.current
        var title by remember { mutableStateOf(serial?.title ?: serial?.title ?: "") }
        var titleRu by remember { mutableStateOf(serial?.titleRu ?: "") }
        var genres by remember { mutableStateOf(serial?.genres ?: "") }
        var releaseYear by remember { mutableStateOf(serial?.releaseYear?.toString() ?: "") }
        var seasons by remember { mutableStateOf(serial?.seasons?.toString() ?: "") }
        var comment by remember { mutableStateOf(serial?.comment ?: "") }
        var status by remember { mutableStateOf(serial?.status?.toString() ?: "") }
        var rating by remember { mutableStateOf(serial?.rating ?: 0) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { localFocusManager.clearFocus() })
                }
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            AppTextField(title, { title = it }, stringResource(R.string.app_title))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(titleRu, { titleRu = it }, stringResource(R.string.app_title_ru))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(genres, { genres = it }, stringResource(R.string.app_genres))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(releaseYear, { releaseYear = it }, stringResource(R.string.app_year),
                KeyboardType.Number)
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(seasons, { seasons = it }, stringResource(R.string.serials_seasons),
                KeyboardType.Number)
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(comment, { comment = it }, stringResource(R.string.app_comment))
            Spacer(modifier = Modifier.height(16.dp))

            RatingBar(rating = rating, onRatingChange = { rating = it })
            Spacer(modifier = Modifier.height(16.dp))

            AppStatusSpinner(
                initItem = serial?.status?.toString(),
                onSelect = { status = it.toString() }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSaveClick(
                        SerialDB(
                            id = serial?.id ?: 0,
                            title = title,
                            titleRu = titleRu,
                            genres = genres,
                            releaseYear = releaseYear.toSafeInt(),
                            seasons = seasons.toSafeInt(),
                            comment = comment,
                            status = ItemStatus.get(status),
                            rating = rating
                        )
                    )
                }
            ) {
                Text(text = stringResource(id = R.string.app_save))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        MovieEditsScreen(
            navController = rememberNavController(),
            serial = SerialDB(
                id = 1,
                title = "Spider-man",
                genres = "Action, Drama",
                releaseYear = 2018,
                seasons = 122,
                rating = 4,
                overview = "overview overview overview overview overview overview overview overview overview overview",
                comment = "overview overview overview overview overview overview overview overview overview overview"
            ),
            onSaveClick = {}
        )
    }
}