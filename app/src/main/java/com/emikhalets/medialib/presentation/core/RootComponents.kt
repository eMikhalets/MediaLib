package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.views.ViewListItem
import com.emikhalets.medialib.utils.formatDate
import com.emikhalets.medialib.utils.px

@Composable
fun RootScreenList(
    query: String,
    list: List<ViewListItem>,
    searchPlaceholder: String,
    onAddClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onItemClick: (Int) -> Unit,
    content: @Composable (ViewListItem) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { localFocusManager.clearFocus() },
                    onTap = { localFocusManager.clearFocus() }
                )
            }
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                leadingIcon = { Icon(Icons.Rounded.Search, "search icon") },
                placeholder = { Text(searchPlaceholder) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
            )
            IconButton(
                onClick = { onAddClick() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        if (list.isEmpty()) {
            val text = if (query.isEmpty()) R.string.app_root_list_empty
            else R.string.app_root_list_query_empty

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(text),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                items(list) { item ->
                    RootScreenListItem(item, onItemClick, content)
                }
            }
        }
    }
}

@Composable
fun RootListItem(
    item: ViewListItem,
    onItemClick: (Int) -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item.id) }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.poster)
                .crossfade(true)
                .transformations(RoundedCornersTransformation(8.px))
                .error(R.drawable.ph_poster)
                .build(),
            contentDescription = "",
            placeholder = painterResource(R.drawable.ph_poster),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(120.dp)
                .padding(8.dp)

        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            if (item.originalTitle.isNotEmpty()) {
                Text(
                    text = item.originalTitle,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                if (item.rating > 0) {
                    Row {
                        repeat(item.rating) {
                            Icon(imageVector = Icons.Default.Star,
                                contentDescription = "",
                                Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = stringResource(R.string.app_date_value, item.releaseDate.formatDate()),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                content()
            }
        }
    }
}

@Composable
fun RootScreenListItem(
    item: ViewListItem,
    onMovieClick: (Int) -> Unit,
    content: @Composable (ViewListItem) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        content = { content(item) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(item.id) }
            .padding(8.dp)
    )
}