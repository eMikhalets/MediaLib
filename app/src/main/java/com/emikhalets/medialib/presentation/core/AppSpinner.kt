package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun AppSpinner(
    list: List<String>,
    initItem: String = list.first(),
    onSelect: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember {
        val item = list.find { it == initItem } ?: list.first()
        mutableStateOf(list.indexOf(item))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedTextField(
            value = list[selectedIndex],
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
            list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        onSelect(item)
                    },
                    content = {
                        Text(text = item)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropDownPreview() {
    AppTheme {
        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            AppSpinner(
                list = listOf("111111", "222222", "333333", "444444", "555555", "666666"),
                initItem = "222222",
                onSelect = {}
            )
        }
    }
}