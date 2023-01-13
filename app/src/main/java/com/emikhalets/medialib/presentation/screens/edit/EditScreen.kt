package com.emikhalets.medialib.presentation.screens.edit

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.presentation.core.AppButton
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppStatusSpinner
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.AppTextFieldDate
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.dialogs.YearDialog
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemStatus
import com.emikhalets.medialib.utils.enums.ItemType
import com.emikhalets.medialib.utils.toSafeInt

@Composable
fun EditScreen(
    navController: NavHostController,
    itemId: Int,
    itemType: ItemType,
    viewModel: EditViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getItemData(itemId, itemType)
    }

    LaunchedEffect(state.saved) {
        if (state.saved) navController.popBackStack()
    }

    EditScreen(
        navController = navController,
        itemId = itemId,
        itemType = itemType,
        item = state.item,
        onSaveClick = { viewModel.saveItem(it, itemType) }
    )
}

@Composable
private fun EditScreen(
    navController: NavHostController,
    itemId: Int,
    itemType: ItemType,
    item: ViewListItem?,
    onSaveClick: (ViewListItem?) -> Unit,
) {
    if (itemId > 0 && item == null) return

    val localFocusManager = LocalFocusManager.current
    var showYearDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf(item?.title ?: "") }
    var titleRu by remember { mutableStateOf(item?.titleRu ?: "") }
    var author by remember { mutableStateOf((item as? BookDB)?.author ?: "") }
    var genres by remember { mutableStateOf(item?.genres ?: "") }
    var releaseYear by remember { mutableStateOf(item?.releaseYear ?: 0) }
    var comment by remember { mutableStateOf(item?.comment ?: "") }
    var seasons by remember { mutableStateOf((item as? SerialDbEntity)?.seasons?.toString() ?: "") }
    var status by remember { mutableStateOf(item?.status?.toString() ?: "") }
    var rating by remember { mutableStateOf(item?.rating ?: 0) }

    AppScaffold(navController, item?.title) {

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

            AppTextField(title, { title = it }, label = stringResource(R.string.title))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(titleRu, { titleRu = it }, label = stringResource(R.string.title_ru))
            Spacer(modifier = Modifier.height(8.dp))

            if (item is BookDB) {
                AppTextField(author, { author = it }, label = stringResource(R.string.author))
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (item is SerialDbEntity) {
                AppTextField(seasons, { seasons = it }, label = stringResource(R.string.seasons))
                Spacer(modifier = Modifier.height(8.dp))
            }

            AppTextField(genres, { genres = it }, label = stringResource(R.string.genres))
            Spacer(modifier = Modifier.height(8.dp))

            AppTextFieldDate(releaseYear.toString(), label = stringResource(R.string.year),
                onClick = { showYearDialog = true })
            Spacer(modifier = Modifier.height(8.dp))

            AppTextField(comment, { comment = it }, label = stringResource(R.string.comment),
                keyboardType = KeyboardType.Number)
            Spacer(modifier = Modifier.height(16.dp))

            RatingBar(rating = rating, onRatingChange = { rating = it })
            Spacer(modifier = Modifier.height(16.dp))

            AppStatusSpinner(
                initItem = item?.status?.toString(),
                onSelect = { status = it.toString() }
            )
            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                text = stringResource(R.string.save),
                onClick = {
                    onSaveClick(
                        item?.buildItem(
                            itemType = itemType,
                            title = title,
                            titleRu = titleRu,
                            author = author,
                            seasons = seasons,
                            genres = genres,
                            releaseYear = releaseYear,
                            comment = comment,
                            status = status,
                            rating = rating
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (showYearDialog) {
            YearDialog(
                year = releaseYear,
                onDismiss = { showYearDialog = false },
                onOkClick = {
                    releaseYear = it
                    showYearDialog = false
                }
            )
        }
    }
}

private fun ViewListItem?.buildItem(
    itemType: ItemType,
    title: String,
    titleRu: String,
    author: String,
    seasons: String,
    genres: String,
    releaseYear: Int,
    comment: String,
    status: String,
    rating: Int,
): ViewListItem? {
    return when (itemType) {
        ItemType.MOVIE -> {
            (this as MovieDbEntity?)?.copy(
                id = this?.id ?: 0,
                title = title,
                titleRu = titleRu,
                genres = genres,
                releaseYear = releaseYear,
                comment = comment,
                status = ItemStatus.get(status),
                rating = rating
            )
        }
        ItemType.SERIAL -> {
            (this as SerialDbEntity?)?.copy(
                id = this?.id ?: 0,
                title = title,
                titleRu = titleRu,
                seasons = seasons.toSafeInt(),
                genres = genres,
                releaseYear = releaseYear,
                comment = comment,
                status = ItemStatus.get(status),
                rating = rating
            )
        }
        ItemType.BOOK -> {
            (this as BookDB?)?.copy(
                id = this?.id ?: 0,
                title = title,
                titleRu = titleRu,
                author = author,
                genres = genres,
                releaseYear = releaseYear,
                comment = comment,
                status = ItemStatus.get(status),
                rating = rating
            )
        }
        ItemType.MUSIC -> {
            // TODO: change to music entity
            (this as MovieDbEntity?)?.copy(
                id = this?.id ?: 0,
                title = title,
                titleRu = titleRu,
                genres = genres,
                releaseYear = releaseYear,
                comment = comment,
                status = ItemStatus.get(status),
                rating = rating
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        EditScreen(
            navController = rememberNavController(),
            itemId = 1,
            itemType = ItemType.BOOK,
            item = BookDB(
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