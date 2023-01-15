package com.emikhalets.medialib.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.core.AppTextField
import com.emikhalets.medialib.presentation.core.TextButtonPrimary
import com.emikhalets.medialib.presentation.core.TextTitle
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun PosterEditDialog(
    url: String,
    onDismiss: () -> Unit,
    onSaveClick: (url: String) -> Unit,
) {
    var urlValue by remember { mutableStateOf(url) }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        DialogLayout(
            url = url,
            onUrlChanged = { urlValue = it },
            onCancelClick = onDismiss,
            onSaveClick = {
                if (urlValue == url) onDismiss()
                else onSaveClick(urlValue)
            }
        )
    }
}

@Composable
private fun DialogLayout(
    url: String,
    onUrlChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
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
            TextTitle(
                text = stringResource(id = R.string.dialog_edit_poster_url_title),
                modifier = Modifier.fillMaxWidth()
            )
            AppTextField(
                value = url,
                onValueChange = onUrlChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                TextButtonPrimary(
                    text = stringResource(id = R.string.dialog_cancel),
                    onClick = onCancelClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                TextButtonPrimary(
                    text = stringResource(id = R.string.dialog_save),
                    onClick = onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        DialogLayout(
            url = "url test",
            onUrlChanged = {},
            onCancelClick = {},
            onSaveClick = {},
        )
    }
}