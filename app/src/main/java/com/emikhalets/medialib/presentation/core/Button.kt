package com.emikhalets.medialib.presentation.core

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(onClick = { onClick() }, modifier = modifier) {
        Text(text = text)
    }
}