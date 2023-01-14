package com.emikhalets.medialib.presentation.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppColors.disabled
import com.emikhalets.medialib.presentation.theme.AppColors.textSecondary
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingIconRes: Int? = null,
    placeholder: String? = null,
    maxLines: Int = 1,
    error: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(modifier = modifier.width(IntrinsicSize.Max)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = if (!label.isNullOrEmpty()) {
                { Text(label) }
            } else null,
            leadingIcon = if (leadingIconRes != null) {
                { Icon(painter = painterResource(leadingIconRes), contentDescription = null) }
            } else null,
            trailingIcon = if (trailingIconRes != null) {
                { Icon(painter = painterResource(trailingIconRes), contentDescription = null) }
            } else null,
            placeholder = if (placeholder != null) {
                { Text(placeholder) }
            } else null,
            maxLines = maxLines,
            enabled = enabled,
            readOnly = readOnly,
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                disabledTextColor = MaterialTheme.colors.disabled,
                backgroundColor = MaterialTheme.colors.background,
                cursorColor = MaterialTheme.colors.onBackground,
                errorCursorColor = MaterialTheme.colors.error,
                focusedBorderColor = MaterialTheme.colors.onBackground,
                unfocusedBorderColor = MaterialTheme.colors.textSecondary,
                disabledBorderColor = MaterialTheme.colors.disabled,
                errorBorderColor = MaterialTheme.colors.error,
                leadingIconColor = MaterialTheme.colors.textSecondary,
                disabledLeadingIconColor = MaterialTheme.colors.disabled,
                errorLeadingIconColor = MaterialTheme.colors.error,
                trailingIconColor = MaterialTheme.colors.textSecondary,
                disabledTrailingIconColor = MaterialTheme.colors.disabled,
                errorTrailingIconColor = MaterialTheme.colors.error,
                focusedLabelColor = MaterialTheme.colors.textSecondary,
                unfocusedLabelColor = MaterialTheme.colors.textSecondary,
                disabledLabelColor = MaterialTheme.colors.disabled,
                errorLabelColor = MaterialTheme.colors.error,
                placeholderColor = MaterialTheme.colors.textSecondary,
                disabledPlaceholderColor = MaterialTheme.colors.disabled
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AppTheme {
        AppTextField(
            value = "Text test",
            onValueChange = {},
            leadingIconRes = R.drawable.ic_round_search_24,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    AppTheme {
        AppTextField(
            value = "Text test",
            onValueChange = {},
            leadingIconRes = R.drawable.ic_round_search_24,
            label = "Label test",
            error = "error",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            leadingIconRes = R.drawable.ic_round_search_24,
            label = "Label test",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DisabledPreview() {
    AppTheme {
        AppTextField(
            value = "Text test",
            onValueChange = {},
            leadingIconRes = R.drawable.ic_round_search_24,
            label = "Label test",
            enabled = false,
            modifier = Modifier.padding(8.dp)
        )
    }
}