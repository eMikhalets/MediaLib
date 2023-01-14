package com.emikhalets.medialib.presentation.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.emikhalets.medialib.R
import com.emikhalets.medialib.utils.px

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

@Composable
fun AppAsyncImage(
    data: String,
    height: Dp,
    modifier: Modifier = Modifier,
    corners: Float = 8.px,
    onClick: (() -> Unit)? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .transformations(RoundedCornersTransformation(corners))
            .error(R.drawable.ph_poster)
            .build(),
        contentDescription = "",
        placeholder = painterResource(R.drawable.ph_poster),
        contentScale = ContentScale.FillHeight,
        modifier = if (onClick != null) {
            modifier
                .height(height)
                .clickable { onClick() }
        } else {
            modifier.height(height)
        }
    )
}