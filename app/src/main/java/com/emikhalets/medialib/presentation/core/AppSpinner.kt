package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.emikhalets.medialib.R
import com.emikhalets.medialib.utils.enums.BookStatus
import com.emikhalets.medialib.utils.enums.MovieStatus

@Composable
fun AppStatusSpinner(
    initItem: String? = null,
    onSelect: (MovieStatus) -> Unit,
) {
    val context = LocalContext.current
    val list = remember {
        MovieStatus.values().toList().map {
            if (it == MovieStatus.NONE) context.getString(R.string.app_status_none)
            else context.getString(it.res)
        }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(initItem ?: list.first()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                if (expanded) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = item
                        expanded = false
                        onSelect(MovieStatus.get(context, item))
                    },
                    content = { Text(text = item) }
                )
            }
        }
    }
}

@Composable
fun AppBookStatusSpinner(
    initItem: String? = null,
    onSelect: (BookStatus) -> Unit,
) {
    val context = LocalContext.current
    val list = remember {
        BookStatus.values().toList().map {
            if (it == BookStatus.NONE) context.getString(R.string.app_status_none)
            else context.getString(it.res)
        }
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(initItem ?: list.first()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                if (expanded) {
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                } else {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded })
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = item
                        expanded = false
                        onSelect(BookStatus.get(context, item))
                    },
                    content = { Text(text = item) }
                )
            }
        }
    }
}