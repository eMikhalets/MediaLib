package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppDialog(
    onDismiss: () -> Unit,
    label: String = "",
    padding: Dp = 8.dp,
    cancelable: Boolean = false,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = !cancelable,
            dismissOnClickOutside = !cancelable,
            usePlatformDefaultWidth = false
        ),
    ) {
        DialogLayout(label, padding, content)
    }
}

@Composable
private fun DialogLayout(label: String, padding: Dp, content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(40.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(Modifier.padding(padding)) {
            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )
            }
            content()
        }
    }
}

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onAddClick: (String, Int, String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    AppDialog(
        label = stringResource(id = R.string.dialog_add_item_title),
        onDismiss = { onDismiss() }
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.add_new_name)) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.add_new_year)) },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(stringResource(R.string.app_comment)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            IconButton(onClick = { onAddClick(name, year.toInt(), comment) }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    AppDialog(
        label = stringResource(id = R.string.dialog_delete_title),
        onDismiss = { onDismiss() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            TextButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(text = stringResource(id = R.string.app_cancel))
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
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

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        AppDialog(
            onDismiss = {},
            label = "Preview label"
        ) {
            Text(text = "Preview dialog text")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddItemDialogPreview() {
    AppTheme {
        AddItemDialog(
            onDismiss = {},
            onAddClick = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteDialogPreview() {
    AppTheme {
        DeleteDialog(
            onDismiss = {},
            onDeleteClick = {}
        )
    }
}