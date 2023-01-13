package com.emikhalets.medialib.presentation.core

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun IconOnPrimary(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        tint = MaterialTheme.colors.onPrimary,
        modifier = modifier
    )
}

@Composable
fun IconPrimary(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        tint = MaterialTheme.colors.onBackground,
        modifier = modifier
    )
}