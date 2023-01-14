package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun AppSpinner(
    selectedItem: String,
    items: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.width(IntrinsicSize.Max)) {
        AppTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            label = label,
            trailingIconRes = if (expanded) {
                R.drawable.ic_round_keyboard_arrow_up_24
            } else {
                R.drawable.ic_round_keyboard_arrow_down_24
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSelect(item)
                    },
                    content = { Text(text = item) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppSpinnerPreview() {
    AppTheme {
        AppSpinner(
            selectedItem = "Test text",
            items = emptyList(),
            onSelect = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}