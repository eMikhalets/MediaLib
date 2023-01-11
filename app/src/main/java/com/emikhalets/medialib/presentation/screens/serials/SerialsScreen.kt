package com.emikhalets.medialib.presentation.screens.serials

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import com.emikhalets.medialib.domain.entities.serials.getIconRes
import com.emikhalets.medialib.presentation.core.AppLoader
import com.emikhalets.medialib.presentation.core.AppTextFullScreen
import com.emikhalets.medialib.presentation.core.IconPrimary
import com.emikhalets.medialib.presentation.core.PosterListItem
import com.emikhalets.medialib.presentation.core.RatingRow
import com.emikhalets.medialib.presentation.core.SearchBox
import com.emikhalets.medialib.presentation.theme.AppTheme
import com.emikhalets.medialib.utils.px

@Composable
fun SerialsScreen(
    navigateToSerialDetails: (serialId: Long) -> Unit,
    navigateToSerialEdit: () -> Unit,
    viewModel: SerialsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getSerialsList()
    }

    if (state.loading) {
        AppLoader()
    } else {
        SerialsScreen(
            query = query,
            serialsList = state.showedSerials,
            onQueryChange = {
                query = it
                viewModel.searchMovies(query)
            },
            onAddSerialClick = { navigateToSerialEdit() },
            onSerialClick = { serialId -> navigateToSerialDetails(serialId) }
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
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBox(
            query = query,
            placeholder = stringResource(id = R.string.serials_search_placeholder),
            onQueryChange = onQueryChange,
            onAddClick = onAddSerialClick
        )

        if (serialsList.isEmpty()) {
            AppTextFullScreen(text = stringResource(id = R.string.serials_empty_list))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(serialsList) { entity ->
                    SerialBox(
                        entity = entity,
                        onMovieClick = onSerialClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SerialBox(
    entity: SerialFullEntity,
    onMovieClick: (serialId: Long) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(entity.serialEntity.id) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        PosterListItem(url = entity.serialEntity.poster)
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = entity.serialEntity.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                val statusIcon = entity.serialEntity.watchStatus.getIconRes()
                if (statusIcon != null) {
                    IconPrimary(drawableRes = statusIcon)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val info = buildString {
                    append(entity.serialEntity.year.toString())
                    if (entity.genres.isNotEmpty()) {
                        append(", ${entity.genres.joinToString { it.name }}")
                    }
                }
                Text(
                    text = info,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (entity.serialEntity.rating > 0) {
                    RatingRow(entity.serialEntity.rating)
                }
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
            onSerialClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    AppTheme {
        SerialBox(
            entity = SerialFullEntity(
                serialEntity = SerialEntity(
                    id = 0,
                    title = "Serial name",
                    titleRu = "",
                    year = 2015,
                    rating = 4,
                    watchStatus = SerialWatchStatus.WATCH,
                    overview = "",
                    poster = "",
                    imdbRating = 0.0,
                    saveTimestamp = 0,
                    lastUpdateTimestamp = 0,
                    comment = ""
                ),
                genres = listOf(
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE),
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE),
                    GenreEntity("Action", GenreType.MOVIE),
                    GenreEntity("Drama", GenreType.MOVIE)
                )
            ),
            onMovieClick = {}
        )
    }
}