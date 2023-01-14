package com.emikhalets.medialib.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.medialib.R
import com.emikhalets.medialib.presentation.theme.AppColors.textSecondary
import com.emikhalets.medialib.presentation.theme.AppTheme

@Composable
fun AppTextFullScreen(text: String? = null) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        TextTitle(
            text = text ?: stringResource(R.string.error_internal),
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TextPrimary(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Start,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.onBackground,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}

@Composable
fun TextSecondary(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Start,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.textSecondary,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}

@Composable
fun TextTitle(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.onBackground,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextPrimary(text = "TextPrimary")
            TextSecondary(text = "TextSecondary")
            TextTitle(text = "TextTitle")
        }
    }
}