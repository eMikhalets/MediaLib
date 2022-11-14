package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.data.entity.views.ViewListItem

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