package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.medialib.presentation.theme.AppColors.disabled
import com.emikhalets.medialib.presentation.theme.AppColors.textLink
import com.emikhalets.medialib.presentation.theme.AppColors.textSecondary
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun ButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            disabledBackgroundColor = MaterialTheme.colors.disabled,
            disabledContentColor = MaterialTheme.colors.textSecondary
        ),
        modifier = modifier.height(56.dp)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun TextButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    TextButton(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.textLink,
            disabledContentColor = MaterialTheme.colors.textSecondary
        ),
        modifier = modifier.height(56.dp)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonPreview() {
    AppTheme {
        ButtonPrimary(
            text = "Test text",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview() {
    AppTheme {
        TextButtonPrimary(
            text = "Test text",
            modifier = Modifier.padding(16.dp)
        )
    }
}