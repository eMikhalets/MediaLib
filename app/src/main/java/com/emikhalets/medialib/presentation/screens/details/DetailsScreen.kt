package com.emikhalets.medialib.presentation.screens.details

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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.domain.entities.compose.MenuIconEntity
import com.emikhalets.medialib.presentation.core.AppAsyncImage
import com.emikhalets.medialib.presentation.core.DetailsSection
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.dialogs.DeleteDialog
import com.emikhalets.medialib.presentation.dialogs.PosterDialog
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.navToItemEdit
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.enums.ItemType

@Composable
fun DetailsScreen(
    navController: NavHostController,
    itemId: Int?,
    itemType: ItemType,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var title by remember { mutableStateOf(context.getString(R.string.screen_name_details)) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPosterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getItem(itemId, itemType)
    }

    AppScaffold(
        navController = navController,
        title = title,
        actions = listOf(
            MenuIconEntity(Icons.Rounded.Edit) {
                val id = (state as? DetailsState.Item)?.item?.id
                if (id != null) navController.navToItemEdit(id, itemType)
            },
            MenuIconEntity(Icons.Rounded.Delete) {
                showDeleteDialog = true
            }
        )
    ) {
        when (state) {

            is DetailsState.Item -> {
                val uiState = state as DetailsState.Item
                title = uiState.item?.title ?: stringResource(R.string.screen_name_details)
                DetailsScreen(
                    item = uiState.item,
                    onRatingChange = { viewModel.updateItem(it, itemType) },
                    onPosterClick = { showPosterDialog = true }
                )
            }
            is DetailsState.Error -> {
                val uiState = state as DetailsState.Error
                AppTextFullScreen(uiState.message)
            }
            DetailsState.ItemDeleted -> navController.popBackStack()
            DetailsState.ItemEmpty -> AppTextFullScreen(stringResource(R.string.error_internal_error))
            DetailsState.Loading -> AppLoader()
        }
    }

    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = { showDeleteDialog = false },
            onDeleteClick = {
                showDeleteDialog = false
                viewModel.deleteItem(itemType)
            }
        )
    }

    if (showPosterDialog) {
        val uiState = state as? DetailsState.Item
        PosterDialog(
            poster = uiState?.item?.poster,
            onDismiss = { showPosterDialog = false },
            onOkClick = {
                showPosterDialog = false
                viewModel.updateItem(it, itemType)
            }
        )
    }
}

@Composable
private fun DetailsScreen(
    item: ViewListItem?,
    onRatingChange: (Int) -> Unit,
    onPosterClick: () -> Unit,
) {
    if (item == null) {
        AppTextFullScreen()
    } else {
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
                    data = item.poster,
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
                        text = item.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    val localeTitle = item.getLocaleTitle()
                    if (localeTitle.isNotEmpty()) {
                        Text(
                            text = localeTitle,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                    if (item is BookDB) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.author,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.releaseYear.toString(),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RatingBar(
                        rating = item.rating,
                        onRatingChange = onRatingChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            DetailsSection(
                header = stringResource(R.string.comment),
                content = item.comment
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.genres_value, item.genres),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            DetailsSection(
                header = stringResource(R.string.overview),
                content = item.overview
            )

            if (item is MovieDbEntity) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.budget_value, item.budget),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.revenue_value, item.revenue),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (item is SerialDbEntity) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.seasons_value, item.seasons),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        DetailsScreen(
            item = MovieDbEntity(
                id = 1,
                title = "Spider-man",
                genres = "Action, Drama",
                releaseYear = 2018,
                rating = 4,
                overview = "overview overview overview overview overview overview overview overview overview overview",
                comment = "overview overview overview overview overview overview overview overview overview overview"
            ),
            onRatingChange = {},
            onPosterClick = {}
        )
    }
}