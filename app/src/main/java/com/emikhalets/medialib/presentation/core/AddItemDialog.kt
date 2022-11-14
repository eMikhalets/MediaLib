package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onAddClick: (String, String, String) -> Unit,
) {
    AppDialog(
        label = stringResource(id = R.string.add_new_title),
        onDismiss = { onDismiss() }
    ) {
        DialogLayout(onAddClick)
    }
}

@Composable
private fun DialogLayout(onAddClick: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

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
            label = { Text(stringResource(R.string.add_new_comment)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        IconButton(onClick = { onAddClick(name, year, comment) }) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        AppDialog(
            onDismiss = {},
            label = stringResource(id = R.string.add_new_title),
            content = { DialogLayout { _, _, _ -> } }
        )
    }
}