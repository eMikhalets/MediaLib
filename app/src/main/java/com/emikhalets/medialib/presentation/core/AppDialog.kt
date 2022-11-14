package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

@Preview(showBackground = true)
@Composable
private fun DialogPreview() {
    AppTheme {
        Column(Modifier.fillMaxSize()) {
            AppDialog(
                onDismiss = {},
                label = "Preview label"
            ) {
                Text(text = "Preview dialog text")
            }
        }
    }
}