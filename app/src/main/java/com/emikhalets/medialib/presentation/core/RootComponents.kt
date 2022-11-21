package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.data.entity.views.ViewListItem
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
fun RootListItem(item: ViewListItem, onItemClick: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item.id) }
            .padding(horizontal = 8.dp, vertical = 4.dp)
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
                .height(70.dp)
                .padding(end = 8.dp)

        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            if (item is BookDB) {
                Text(
                    text = item.author,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (item is SerialDB) {
                Text(
                    text = stringResource(R.string.serials_seasons_value, item.seasons),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${item.releaseYear}, ${item.genres}",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (item.rating > 0) {
                    Row {
                        repeat(5) {
                            Icon(imageVector = Icons.Default.Star,
                                contentDescription = "",
                                tint = if (it < item.rating) Color.Black else Color.Gray,
                                modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RootSaveDelete(
    isNeedSave: Boolean,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            if (isNeedSave) {
                Button(
                    onClick = { onSaveClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.app_save))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Button(
                onClick = { onDeleteClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.app_delete))
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

@Composable
fun AppAsyncImage(
    data: String,
    height: Dp,
    modifier: Modifier = Modifier,
    corners: Float = 8.px,
    onClick: () -> Unit = {},
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .transformations(RoundedCornersTransformation(corners))
            .error(R.drawable.ph_poster)
            .build(),
        contentDescription = "",
        placeholder = painterResource(R.drawable.ph_poster),
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .height(height)
            .padding(8.dp)
            .clickable { onClick() }
    )
}

@Composable
fun AppDetailsSection(header: String, content: String) {
    if (content.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = header,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = content,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = if (label.isNotEmpty()) {
            { Text(label) }
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = KeyboardCapitalization.Sentences
        ),
        modifier = Modifier.fillMaxWidth()
    )
}