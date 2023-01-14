package com.emikhalets.medialib.presentation.screens.serials.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus.Companion.getIconRes
import com.emikhalets.medialib.presentation.core.AppAsyncImage
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.AppTopBar
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.core.RatingBar
import com.emikhalets.medialib.presentation.core.SearchBox
import com.emikhalets.medialib.presentation.core.TextPrimary
import com.emikhalets.medialib.presentation.core.TextSecondary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.toast

@Composable
fun SerialsScreen(
    navigateToSerialDetails: (serialId: Long) -> Unit,
    navigateToSerialEdit: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SerialsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getSerialsList()
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            val message = error.asString(context)
            message.toast(context)
            viewModel.resetError()
        }
    }

    if (state.loading) {
        AppLoader()
    } else {
        SerialsScreen(
            query = query,
            serialsList = state.showedSerials,
            onQueryChange = {
                query = it
                viewModel.searchSerials(query)
            },
            onAddSerialClick = { navigateToSerialEdit() },
            onSerialClick = { serialId -> navigateToSerialDetails(serialId) },
            onBackClick = navigateBack
        )
    }
}

@Composable
private fun SerialsScreen(
    query: String,
    serialsList: List<SerialFullEntity>,
    onQueryChange: (String) -> Unit,
    onAddSerialClick: () -> Unit,
    onSerialClick: (serialId: Long) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(id = R.string.screen_title_serials),
            onNavigateBack = onBackClick
        )
        SearchBox(
            query = query,
            placeholder = stringResource(id = R.string.serials_search_placeholder),
            onQueryChange = onQueryChange,
            onAddClick = onAddSerialClick,
            modifier = Modifier.padding(8.dp)
        )

        if (serialsList.isEmpty()) {
            AppTextFullScreen(text = stringResource(id = R.string.serials_empty_list))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(serialsList) { entity ->
                    SerialBox(
                        entity = entity,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onSerialClick(entity.serialEntity.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SerialBox(
    entity: SerialFullEntity,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AppAsyncImage(
            url = entity.serialEntity.poster,
            height = 100.dp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextTitle(
                    text = entity.serialEntity.title,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                val statusIcon = entity.serialEntity.watchStatus.getIconRes()
                if (statusIcon != null) {
                    IconPrimary(drawableRes = statusIcon)
                }
            }
            if (entity.serialEntity.titleRu.isNotEmpty()) {
                TextSecondary(
                    text = entity.serialEntity.titleRu,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (entity.serialEntity.rating > 0) {
                RatingBar(
                    rating = entity.serialEntity.rating,
                    pointSize = 14.dp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            val info = entity.formatListItemInfo()
            if (info.isNotEmpty()) {
                TextPrimary(
                    text = info,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    AppTheme {
        SerialsScreen(
            query = "",
            serialsList = emptyList(),
            onQueryChange = {},
            onAddSerialClick = {},
            onSerialClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SerialItemPreview() {
    AppTheme {
        SerialBox(
            entity = SerialFullEntity(
                serialEntity = SerialEntity(
                    id = 0,
                    title = "Serial name",
                    titleRu = "Serial name",
                    year = 2015,
                    rating = 4,
                    watchStatus = SerialWatchStatus.WATCH,
                    overview = "",
                    poster = "",
                    saveTimestamp = 0,
                    lastUpdateTimestamp = 0,
                    comment = ""
                ),
                genres = listOf(
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL),
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL),
                    GenreEntity("Action", GenreType.SERIAL),
                    GenreEntity("Drama", GenreType.SERIAL)
                ),
                ratings = emptyList()
            )
        )
    }
}