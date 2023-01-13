package com.emikhalets.medialib.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun DeleteFromDbDialog(
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        DialogLayout(
            onCancelClick = onDismiss,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
private fun DialogLayout(
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp)
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.dialog_delete_entity_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 8.dp, end = 8.dp)
            ) {
                TextButton(
                    onClick = { onCancelClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.dialog_cancel))
                }
                TextButton(
                    onClick = { onDeleteClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = stringResource(id = R.string.dialog_delete))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        DialogLayout(
            onCancelClick = {},
            onDeleteClick = {}
        )
    }
}