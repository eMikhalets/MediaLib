package com.emikhalets.medialib.presentation.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    @DrawableRes leadingIconRes: Int? = null,
    placeholder: String? = null,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = if (!label.isNullOrEmpty()) {
            { Text(label) }
        } else null,
        leadingIcon = if (leadingIconRes != null) {
            { IconTextField(drawableRes = leadingIconRes) }
        } else null,
        placeholder = if (placeholder != null) {
            { Text(placeholder) }
        } else null,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = KeyboardCapitalization.Sentences
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
            disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
        ),
        modifier = modifier.fillMaxWidth()
    )
}

// TODO: refactor as simple text with border
@Composable
fun AppTextFieldDate(
    value: String,
    label: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = if (value == "0") "" else value,
        onValueChange = {},
        label = if (!label.isNullOrEmpty()) {
            { Text(label) }
        } else null,
        maxLines = 1,
        readOnly = true,
        enabled = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
            disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
}