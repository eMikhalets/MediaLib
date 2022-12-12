package com.emikhalets.medialib.presentation.screens.details

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
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.support.MenuIconEntity
import com.emikhalets.medialib.data.entity.support.ViewListItem
import com.emikhalets.medialib.presentation.core.AppAsyncImage
import com.emikhalets.medialib.presentation.core.AppDetailsSection
import com.emikhalets.medialib.presentation.core.AppScaffold
import com.emikhalets.medialib.presentation.core.DeleteDialog
import com.emikhalets.medialib.presentation.core.PosterDialog
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
    val state by viewModel.state.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPosterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getItem(itemId, itemType)
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) navController.popBackStack()
    }

    DetailsScreen(
        navController = navController,
        item = state.item,
        onRatingChange = { viewModel.updateItem(it, itemType) },
        onDeleteClick = { showDeleteDialog = true },
        onPosterClick = { showPosterDialog = true },
        onEditClick = { navController.navToItemEdit(state.item?.id ?: 0, itemType) },
    )

    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = { showDeleteDialog = false },
            onDeleteClick = {
                viewModel.deleteItem(itemType)
                showDeleteDialog = false
            }
        )
    }

    if (showPosterDialog) {
        PosterDialog(
            poster = state.item?.poster,
            onDismiss = { showPosterDialog = false },
            onOkClick = {
                viewModel.updateItem(it, itemType)
                showPosterDialog = false
            }
        )
    }
}

@Composable
private fun DetailsScreen(
    navController: NavHostController,
    item: ViewListItem?,
    onRatingChange: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onPosterClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    AppScaffold(
        navController = navController,
        title = item?.title,
        actions = listOf(
            MenuIconEntity(Icons.Rounded.Edit) { onEditClick() },
            MenuIconEntity(Icons.Rounded.Delete) { onDeleteClick() }
        )
    ) {
        if (item == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.loading_error),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            ItemLayout(
                item = item,
                onRatingChange = onRatingChange,
                onPosterClick = onPosterClick,
            )
        }
    }
}

@Composable
private fun ItemLayout(
    item: ViewListItem,
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
        AppDetailsSection(
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
        AppDetailsSection(
            header = stringResource(R.string.overview),
            content = item.overview
        )

        if (item is MovieDB) {
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

        if (item is SerialDB) {
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

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        DetailsScreen(
            navController = rememberNavController(),
            item = MovieDB(
                id = 1,
                title = "Spider-man",
                genres = "Action, Drama",
                releaseYear = 2018,
                rating = 4,
                overview = "overview overview overview overview overview overview overview overview overview overview",
                comment = "overview overview overview overview overview overview overview overview overview overview"
            ),
            onRatingChange = {},
            onDeleteClick = {},
            onPosterClick = {},
            onEditClick = {},
        )
    }
}